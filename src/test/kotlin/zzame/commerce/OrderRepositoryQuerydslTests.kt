package zzame.commerce

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import zzame.commerce.common.config.QuerydslConfig
import zzame.commerce.order.dto.OrderSearchCondition
import zzame.commerce.order.entity.Order
import zzame.commerce.order.entity.PaymentStatus
import zzame.commerce.order.repository.OrderRepository
import zzame.commerce.product.entity.Product
import zzame.commerce.product.entity.Seller
import zzame.commerce.product.repository.ProductRepository
import zzame.commerce.product.repository.SellerRepository

@DataJpaTest
@Import(QuerydslConfig::class)
class OrderRepositoryQuerydslTests {

    @Autowired
    lateinit var orderRepository: OrderRepository

    @Autowired
    lateinit var sellerRepository: SellerRepository

    @Autowired
    lateinit var productRepository: ProductRepository

    @Test
    fun `search filters by customer and payment status`() {
        val (product, option) = createProductWithOption()
        val paidOrder = saveOrder(customerId = 1L, product = product, quantity = 1).apply { markPaid() }
        saveOrder(customerId = 2L, product = product, quantity = 1)
        orderRepository.saveAndFlush(paidOrder)

        val result = orderRepository.search(
            condition = OrderSearchCondition(
                customerId = 1L,
                paymentStatus = PaymentStatus.PAID,
            ),
            pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "orderedAt")),
        )

        assertThat(result.content).hasSize(1)
        assertThat(result.content.first().customerId).isEqualTo(1L)
        assertThat(result.content.first().paymentStatus).isEqualTo(PaymentStatus.PAID)
        assertThat(option.name).isEqualTo("기본 옵션")
    }

    @Test
    fun `search applies total price sorting`() {
        val (product, _) = createProductWithOption()
        saveOrder(customerId = 3L, product = product, quantity = 1)
        saveOrder(customerId = 4L, product = product, quantity = 3)

        val result = orderRepository.search(
            condition = OrderSearchCondition(),
            pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "totalPrice")),
        )

        assertThat(result.content).hasSize(2)
        assertThat(result.content.first().totalQuantity).isEqualTo(3)
        assertThat(result.content.first().totalPrice).isGreaterThan(result.content.last().totalPrice)
    }

    private fun createProductWithOption(): Pair<Product, zzame.commerce.product.entity.ProductOption> {
        val seller = sellerRepository.save(Seller.create("셀러", "seller@example.com"))
        val product = productRepository.save(
            Product.create(seller, "키보드", 50000L, "기계식 키보드").apply {
                addOption("기본 옵션", 2000L, 20)
            },
        )

        return product to product.options.first()
    }

    private fun saveOrder(customerId: Long, product: Product, quantity: Int): Order {
        val cart = zzame.commerce.cart.entity.Cart.create(customerId).apply {
            addItem(product, product.options.first(), quantity)
        }

        return orderRepository.save(
            Order.create(
                customerId = customerId,
                recipientName = "주문자",
                phone = "010-0000-0000",
                address = "서울시 중구",
                detailAddress = "301호",
                deliveryRequest = null,
                cartItems = cart.items,
            ),
        )
    }
}
