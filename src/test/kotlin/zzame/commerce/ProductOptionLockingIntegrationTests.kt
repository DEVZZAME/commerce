package zzame.commerce

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.orm.ObjectOptimisticLockingFailureException
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import zzame.commerce.product.entity.Product
import zzame.commerce.product.entity.Seller
import zzame.commerce.product.repository.ProductOptionRepository
import zzame.commerce.product.repository.ProductRepository
import zzame.commerce.product.repository.SellerRepository

@SpringBootTest
class ProductOptionLockingIntegrationTests {

    @Autowired
    lateinit var productOptionRepository: ProductOptionRepository

    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var sellerRepository: SellerRepository

    @Autowired
    lateinit var transactionManager: PlatformTransactionManager

    @Test
    fun `pessimistic lock query returns target option`() {
        val optionId = saveOption()
        val lockedOptions = TransactionTemplate(transactionManager).execute {
            productOptionRepository.findAllForUpdateByIdIn(listOf(optionId))
        }!!

        assertThat(lockedOptions).hasSize(1)
        assertThat(lockedOptions.first().id).isEqualTo(optionId)
        assertThat(lockedOptions.first().product.name).isEqualTo("키보드")
    }

    @Test
    fun `optimistic lock detects stale update with version field`() {
        val optionId = saveOption()
        val transactionTemplate = TransactionTemplate(transactionManager)

        val firstRead = transactionTemplate.execute {
            productOptionRepository.findById(optionId).orElseThrow()
        }!!
        val secondRead = transactionTemplate.execute {
            productOptionRepository.findById(optionId).orElseThrow()
        }!!

        transactionTemplate.executeWithoutResult {
            firstRead.updateStock(3)
            productOptionRepository.saveAndFlush(firstRead)
        }

        assertThrows<ObjectOptimisticLockingFailureException> {
            transactionTemplate.executeWithoutResult {
                secondRead.updateStock(1)
                productOptionRepository.saveAndFlush(secondRead)
            }
        }
    }

    private fun saveOption(): Long {
        val seller = sellerRepository.save(
            Seller.create("셀러", "seller-${System.nanoTime()}@example.com"),
        )
        val product = productRepository.save(
            Product.create(seller, "키보드", 50000L, "기계식 키보드").apply {
                addOption("적축", 3000L, 10)
            },
        )

        return product.options.first().id
    }
}
