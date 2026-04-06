package zzame.commerce

import jakarta.persistence.EntityManager
import jakarta.persistence.EntityManagerFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import zzame.commerce.common.config.QuerydslConfig
import zzame.commerce.product.dto.ProductSearchCondition
import zzame.commerce.product.entity.Product
import zzame.commerce.product.entity.ProductStatus
import zzame.commerce.product.entity.Seller
import zzame.commerce.product.repository.ProductRepository
import zzame.commerce.product.repository.SellerRepository

@DataJpaTest
@Import(QuerydslConfig::class)
class ProductRepositoryIntegrationTests {

    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var sellerRepository: SellerRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var entityManagerFactory: EntityManagerFactory

    @Test
    fun `search applies keyword seller and status filters`() {
        val sellerOne = sellerRepository.save(
            Seller.create("Seller One", "seller-one@example.com"),
        )
        val sellerTwo = sellerRepository.save(
            Seller.create("Seller Two", "seller-two@example.com"),
        )

        val keyboard = Product.create(sellerOne, "Gaming Keyboard", 150000L, "RGB keyboard")
        val mouse = Product.create(sellerOne, "Gaming Mouse", 50000L, "Light mouse")
        val monitor = Product.create(sellerTwo, "Monitor", 300000L, "4K monitor")
        mouse.hide()

        productRepository.saveAll(listOf(keyboard, mouse, monitor))

        val result = productRepository.search(
            condition = ProductSearchCondition(
                keyword = "gaming",
                sellerId = sellerOne.id,
                status = ProductStatus.ON_SALE,
                minPrice = 100000L,
            ),
            pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "price")),
        )

        assertThat(result.content).hasSize(1)
        assertThat(result.content.first().name).isEqualTo("Gaming Keyboard")
        assertThat(result.content.first().sellerName).isEqualTo("Seller One")
    }

    @Test
    fun `search applies paging and sorting`() {
        val seller = sellerRepository.save(
            Seller.create("Seller One", "seller-one@example.com"),
        )

        productRepository.saveAll(
            listOf(
                Product.create(seller, "Keyboard", 50000L, null),
                Product.create(seller, "Monitor", 200000L, null),
                Product.create(seller, "Mouse", 30000L, null),
            ),
        )

        val result = productRepository.search(
            condition = ProductSearchCondition(),
            pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "price")),
        )

        assertThat(result.content).hasSize(2)
        assertThat(result.content.map { it.name }).containsExactly("Monitor", "Keyboard")
        assertThat(result.totalElements).isEqualTo(3)
    }

    @Test
    fun `find detail by id loads seller and options with fetch join`() {
        val seller = sellerRepository.save(
            Seller.create("Seller One", "seller-one@example.com"),
        )
        val product = Product.create(seller, "Keyboard", 50000L, "Mechanical keyboard")
        product.addOption("Red Switch", 0L, 10)
        product.addOption("Blue Switch", 5000L, 5)
        val persistedProduct = productRepository.save(product)

        entityManager.flush()
        entityManager.clear()

        val detail = productRepository.findDetailById(persistedProduct.id)

        val persistenceUtil = entityManagerFactory.persistenceUnitUtil
        assertThat(detail).isNotNull
        assertThat(persistenceUtil.isLoaded(detail, "seller")).isTrue()
        assertThat(persistenceUtil.isLoaded(detail, "options")).isTrue()
        assertThat(detail!!.seller.name).isEqualTo("Seller One")
        assertThat(detail.options).hasSize(2)
    }
}
