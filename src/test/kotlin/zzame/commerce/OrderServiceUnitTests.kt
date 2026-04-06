package zzame.commerce

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import zzame.commerce.cart.entity.Cart
import zzame.commerce.cart.repository.CartRepository
import zzame.commerce.order.dto.OrderCreateRequest
import zzame.commerce.order.repository.OrderRepository
import zzame.commerce.order.service.OrderService
import zzame.commerce.product.entity.Product
import zzame.commerce.product.entity.ProductStatus
import zzame.commerce.product.entity.Seller
import zzame.commerce.product.repository.ProductOptionRepository

class OrderServiceUnitTests {

    private val orderRepository: OrderRepository = mock()
    private val cartRepository: CartRepository = mock()
    private val productOptionRepository: ProductOptionRepository = mock()

    private val orderService = OrderService(
        orderRepository = orderRepository,
        cartRepository = cartRepository,
        productOptionRepository = productOptionRepository,
    )

    @Test
    fun `create order saves order and clears cart items`() {
        val customerId = 1L
        val cart = createCart(customerId, stockQuantity = 10, quantity = 2)

        whenever(cartRepository.findByCustomerId(customerId)).thenReturn(cart)
        whenever(orderRepository.save(any<zzame.commerce.order.entity.Order>())).thenAnswer { invocation ->
            invocation.arguments[0].let { it as zzame.commerce.order.entity.Order }.apply { id = 100L }
        }
        whenever(orderRepository.findDetailById(100L)).thenReturn(null)

        val response = orderService.createOrder(
            OrderCreateRequest(
                customerId = customerId,
                recipientName = "홍길동",
                phone = "010-1111-2222",
                address = "서울시 강남구",
                detailAddress = "101동",
                deliveryRequest = "문 앞 배송",
            ),
        )

        assertThat(response.customerId).isEqualTo(customerId)
        assertThat(response.totalQuantity).isEqualTo(2)
        assertThat(cart.items).isEmpty()
    }

    @Test
    fun `complete payment deducts stock and marks product as sold out`() {
        val cart = createCart(customerId = 2L, stockQuantity = 2, quantity = 2)
        val order = zzame.commerce.order.entity.Order.create(
            customerId = 2L,
            recipientName = "주문자",
            phone = "010-2222-3333",
            address = "서울시 송파구",
            detailAddress = "202호",
            deliveryRequest = null,
            cartItems = cart.items,
        ).apply { id = 200L }
        val lockedOption = cart.items.first().productOption

        whenever(orderRepository.findDetailByIdForUpdate(200L)).thenReturn(order)
        whenever(productOptionRepository.findAllForUpdateByIdIn(listOf(lockedOption.id))).thenReturn(listOf(lockedOption))
        whenever(productOptionRepository.existsByProductIdAndStockQuantityGreaterThan(order.items.first().product.id, 0))
            .thenReturn(false)
        whenever(orderRepository.findDetailById(200L)).thenReturn(order)

        val response = orderService.completePayment(200L)

        assertThat(response.paymentStatus.name).isEqualTo("PAID")
        assertThat(lockedOption.stockQuantity).isZero()
        assertThat(order.items.first().product.status).isEqualTo(ProductStatus.SOLD_OUT)
    }

    private fun createCart(customerId: Long, stockQuantity: Int, quantity: Int): Cart {
        val seller = Seller.create(
            name = "셀러",
            email = "seller@example.com",
        ).apply { id = 10L }
        val product = Product.create(
            seller = seller,
            name = "키보드",
            price = 50000L,
            description = "테스트 상품",
        ).apply { id = 20L }
        val option = product.addOption(
            name = "적축",
            additionalPrice = 3000L,
            stockQuantity = stockQuantity,
            description = "기본 옵션",
        ).apply { id = 30L }

        return Cart.create(customerId).apply {
            id = 40L
            addItem(
                product = product,
                productOption = option,
                quantity = quantity,
            )
        }
    }
}
