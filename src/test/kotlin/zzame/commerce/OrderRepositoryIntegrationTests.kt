package zzame.commerce

import jakarta.persistence.EntityManagerFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import zzame.commerce.common.config.QuerydslConfig
import zzame.commerce.order.entity.Order
import zzame.commerce.order.repository.OrderRepository
import zzame.commerce.product.entity.Product
import zzame.commerce.product.entity.Seller
import zzame.commerce.product.repository.ProductRepository
import zzame.commerce.product.repository.SellerRepository

@DataJpaTest
@Import(QuerydslConfig::class)
class OrderRepositoryIntegrationTests {

    @Autowired
    lateinit var orderRepository: OrderRepository

    @Autowired
    lateinit var sellerRepository: SellerRepository

    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var entityManagerFactory: EntityManagerFactory

    @Test
    fun `find detail by id for update loads item product and option graph`() {
        val seller = sellerRepository.save(Seller.create("셀러", "seller@example.com"))
        val product = productRepository.save(
            Product.create(seller, "키보드", 50000L, "기계식 키보드").apply {
                addOption("적축", 3000L, 5)
            },
        )
        val order = orderRepository.save(
            Order.create(
                customerId = 1L,
                recipientName = "주문자",
                phone = "010-1234-5678",
                address = "서울시 강남구",
                detailAddress = "101호",
                deliveryRequest = "문 앞 배송",
                cartItems = listOf(
                    zzame.commerce.cart.entity.Cart.create(1L).apply {
                        addItem(product, product.options.first(), 1)
                    }.items.first(),
                ),
            ),
        )

        val detail = orderRepository.findDetailByIdForUpdate(order.id)
        val persistenceUtil = entityManagerFactory.persistenceUnitUtil

        assertThat(detail).isNotNull
        assertThat(detail!!.items).hasSize(1)
        assertThat(persistenceUtil.isLoaded(detail.items.first(), "product")).isTrue()
        assertThat(persistenceUtil.isLoaded(detail.items.first(), "productOption")).isTrue()
    }
}
