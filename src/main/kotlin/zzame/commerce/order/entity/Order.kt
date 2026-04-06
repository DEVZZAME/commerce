package zzame.commerce.order.entity

import zzame.commerce.cart.entity.CartItem
import zzame.commerce.common.util.trimToNull
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
class Order protected constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    @Column(nullable = false, unique = true, length = 50)
    var orderNumber: String = "",

    @Column(nullable = false)
    var customerId: Long = 0L,

    @Column(nullable = false, length = 100)
    var recipientName: String = "",

    @Column(nullable = false, length = 30)
    var phone: String = "",

    @Column(nullable = false, length = 255)
    var address: String = "",

    @Column(nullable = false, length = 255)
    var detailAddress: String = "",

    @Column(length = 500)
    var deliveryRequest: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var status: OrderStatus = OrderStatus.CREATED,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var paymentStatus: PaymentStatus = PaymentStatus.READY,

    @Column(nullable = false)
    var totalQuantity: Int = 0,

    @Column(nullable = false)
    var totalPrice: Long = 0L,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    private val itemList: MutableList<OrderItem> = mutableListOf(),

    @Column(nullable = false, updatable = false)
    var orderedAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    val items: List<OrderItem>
        get() = itemList.toList()

    companion object {
        fun create(
            customerId: Long,
            recipientName: String,
            phone: String,
            address: String,
            detailAddress: String,
            deliveryRequest: String?,
            cartItems: List<CartItem>,
        ): Order {
            require(customerId > 0) { "customerId must be greater than 0" }
            require(cartItems.isNotEmpty()) { "order items must not be empty" }

            val order = Order(
                orderNumber = generateOrderNumber(),
                customerId = customerId,
                recipientName = normalizeText(recipientName, "recipientName"),
                phone = normalizeText(phone, "phone"),
                address = normalizeText(address, "address"),
                detailAddress = normalizeText(detailAddress, "detailAddress"),
                deliveryRequest = deliveryRequest.trimToNull(),
            )

            cartItems.forEach { cartItem ->
                order.itemList.add(OrderItem.from(order, cartItem))
            }

            order.totalQuantity = order.itemList.sumOf(OrderItem::quantity)
            order.totalPrice = order.itemList.sumOf(OrderItem::linePrice)

            return order
        }

        private fun normalizeText(value: String, fieldName: String): String = value.trim()
            .takeIf { it.isNotEmpty() }
            ?: throw IllegalArgumentException("$fieldName must not be blank")

        private fun generateOrderNumber(): String =
            "ORD-${System.currentTimeMillis()}"
    }

    fun markPaid(paidAt: LocalDateTime = LocalDateTime.now()) {
        status = OrderStatus.PAID
        paymentStatus = PaymentStatus.PAID
        orderedAt = paidAt
        updatedAt = LocalDateTime.now()
    }

    fun cancel() {
        status = OrderStatus.CANCELED
        paymentStatus = PaymentStatus.CANCELED
        updatedAt = LocalDateTime.now()
    }

    @PrePersist
    fun prePersist() {
        val now = LocalDateTime.now()
        createdAt = now
        updatedAt = now
        if (orderedAt == LocalDateTime.MIN) {
            orderedAt = now
        }
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = LocalDateTime.now()
    }
}
