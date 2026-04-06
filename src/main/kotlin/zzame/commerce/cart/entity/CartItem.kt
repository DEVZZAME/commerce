package zzame.commerce.cart.entity

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
@Table(name = "cart_items")
class CartItem protected constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    @Column(nullable = false)
    var quantity: Int = 0,

    @Column(nullable = false)
    var unitPrice: Long = 0L,

    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    lateinit var cart: Cart

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    lateinit var product: Product

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_option_id", nullable = false)
    lateinit var productOption: ProductOption

    companion object {
        fun create(
            cart: Cart,
            product: Product,
            productOption: ProductOption,
            quantity: Int,
            unitPrice: Long,
        ): CartItem = CartItem(
            quantity = requirePositiveQuantity(quantity),
            unitPrice = requirePositivePrice(unitPrice),
        ).apply {
            this.cart = cart
            this.product = product
            this.productOption = productOption
        }

        private fun requirePositiveQuantity(quantity: Int): Int = quantity
            .takeIf { it > 0 }
            ?: throw IllegalArgumentException("quantity must be greater than 0")

        private fun requirePositivePrice(price: Long): Long = price
            .takeIf { it > 0 }
            ?: throw IllegalArgumentException("unitPrice must be greater than 0")
    }

    fun increaseQuantity(quantity: Int) {
        this.quantity += requirePositiveQuantity(quantity)
        updatedAt = LocalDateTime.now()
    }

    fun linePrice(): Long = unitPrice * quantity

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
