package zzame.commerce

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import zzame.commerce.cart.entity.Cart
import zzame.commerce.order.entity.Order
import zzame.commerce.order.entity.OrderStatus
import zzame.commerce.order.entity.PaymentStatus
import zzame.commerce.product.entity.Product
import zzame.commerce.product.entity.Seller

class OrderEntityTests {

    @Test
    fun `order create snapshots cart items and calculates totals`() {
        val seller = Seller.create(
            name = "셀러 원",
            email = "seller@example.com",
        )
        val product = Product.create(
            seller = seller,
            name = "키보드",
            price = 49000L,
            description = "기계식 키보드",
        )
        val option = product.addOption(
            name = "적축",
            additionalPrice = 5000L,
            stockQuantity = 10,
            description = "조용한 키감",
        )
        val cart = Cart.create(customerId = 100L)
        val cartItem = cart.addItem(
            product = product,
            productOption = option,
            quantity = 2,
        )

        val order = Order.create(
            customerId = 100L,
            recipientName = "  홍길동  ",
            phone = " 010-1234-5678 ",
            address = "  서울시 강남구 테헤란로 1 ",
            detailAddress = "  101동 202호 ",
            deliveryRequest = "  문 앞에 놓아주세요 ",
            cartItems = listOf(cartItem),
        )

        assertThat(order.id).isZero()
        assertThat(order.orderNumber).startsWith("ORD-")
        assertThat(order.customerId).isEqualTo(100L)
        assertThat(order.recipientName).isEqualTo("홍길동")
        assertThat(order.phone).isEqualTo("010-1234-5678")
        assertThat(order.address).isEqualTo("서울시 강남구 테헤란로 1")
        assertThat(order.detailAddress).isEqualTo("101동 202호")
        assertThat(order.deliveryRequest).isEqualTo("문 앞에 놓아주세요")
        assertThat(order.status).isEqualTo(OrderStatus.CREATED)
        assertThat(order.paymentStatus).isEqualTo(PaymentStatus.READY)
        assertThat(order.totalQuantity).isEqualTo(2)
        assertThat(order.totalPrice).isEqualTo(108000L)
        assertThat(order.items).hasSize(1)
        assertThat(order.items.first().productName).isEqualTo("키보드")
        assertThat(order.items.first().optionName).isEqualTo("적축")
        assertThat(order.items.first().sellerName).isEqualTo("셀러 원")
        assertThat(order.items.first().quantity).isEqualTo(2)
        assertThat(order.items.first().unitPrice).isEqualTo(54000L)
        assertThat(order.items.first().linePrice).isEqualTo(108000L)
    }

    @Test
    fun `order can transition to paid`() {
        val order = createOrder()

        order.markPaid()

        assertThat(order.status).isEqualTo(OrderStatus.PAID)
        assertThat(order.paymentStatus).isEqualTo(PaymentStatus.PAID)
    }

    @Test
    fun `order create rejects empty items`() {
        assertThatThrownBy {
            Order.create(
                customerId = 100L,
                recipientName = "홍길동",
                phone = "010-1234-5678",
                address = "서울시 강남구",
                detailAddress = "101동",
                deliveryRequest = null,
                cartItems = emptyList(),
            )
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("order items must not be empty")
    }

    private fun createOrder(): Order {
        val seller = Seller.create(
            name = "셀러 원",
            email = "seller@example.com",
        )
        val product = Product.create(
            seller = seller,
            name = "모니터",
            price = 200000L,
            description = null,
        )
        val option = product.addOption(
            name = "27인치",
            additionalPrice = 0L,
            stockQuantity = 3,
        )
        val cart = Cart.create(customerId = 101L)
        val cartItem = cart.addItem(
            product = product,
            productOption = option,
            quantity = 1,
        )

        return Order.create(
            customerId = 101L,
            recipientName = "김주문",
            phone = "010-2222-3333",
            address = "부산시 해운대구",
            detailAddress = "센텀시티 101",
            deliveryRequest = null,
            cartItems = listOf(cartItem),
        )
    }
}
