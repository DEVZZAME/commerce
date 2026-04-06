package zzame.commerce.order.entity

import zzame.commerce.cart.entity.CartItem
import zzame.commerce.product.entity.Product
import zzame.commerce.product.entity.ProductOption
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "order_items")
class OrderItem protected constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    @Column(nullable = false, length = 100)
    var productName: String = "",

    @Column(nullable = false, length = 100)
    var optionName: String = "",

    @Column(nullable = false, length = 100)
    var sellerName: String = "",

    @Column(nullable = false)
    var quantity: Int = 0,

    @Column(nullable = false)
    var unitPrice: Long = 0L,

    @Column(nullable = false)
    var linePrice: Long = 0L,

    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    lateinit var order: Order

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    lateinit var product: Product

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_option_id", nullable = false)
    lateinit var productOption: ProductOption

    companion object {
        fun from(order: Order, cartItem: CartItem): OrderItem = OrderItem(
            productName = cartItem.product.name,
            optionName = cartItem.productOption.name,
            sellerName = cartItem.product.seller.name,
            quantity = cartItem.quantity,
            unitPrice = cartItem.unitPrice,
            linePrice = cartItem.linePrice(),
        ).apply {
            this.order = order
            this.product = cartItem.product
            this.productOption = cartItem.productOption
        }
    }

    @PrePersist
    fun prePersist() {
        val now = LocalDateTime.now()
        createdAt = now
        updatedAt = now
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = LocalDateTime.now()
    }
}
