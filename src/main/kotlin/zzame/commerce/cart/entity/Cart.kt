package zzame.commerce.cart.entity

import zzame.commerce.product.entity.Product
import zzame.commerce.product.entity.ProductOption
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "carts")
class Cart protected constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    @Column(nullable = false, unique = true)
    var customerId: Long = 0L,

    @OneToMany(mappedBy = "cart", cascade = [CascadeType.ALL], orphanRemoval = true)
    private val itemList: MutableList<CartItem> = mutableListOf(),

    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    val items: List<CartItem>
        get() = itemList.sortedBy(CartItem::id)

    companion object {
        fun create(customerId: Long): Cart = Cart(
            customerId = customerId.takeIf { it > 0 }
                ?: throw IllegalArgumentException("customerId must be greater than 0"),
        )
    }

    fun addItem(
        product: Product,
        productOption: ProductOption,
        quantity: Int,
    ): CartItem {
        val existingItem = itemList.find { item ->
            item.product.id == product.id && item.productOption.id == productOption.id
        }

        if (existingItem != null) {
            existingItem.increaseQuantity(quantity)
            updatedAt = LocalDateTime.now()
            return existingItem
        }

        return CartItem.create(
            cart = this,
            product = product,
            productOption = productOption,
            quantity = quantity,
            unitPrice = product.price + productOption.additionalPrice,
        ).also {
            itemList.add(it)
            updatedAt = LocalDateTime.now()
        }
    }

    fun removeItem(cartItemId: Long): Boolean {
        val removed = itemList.removeIf { it.id == cartItemId }

        if (removed) {
            updatedAt = LocalDateTime.now()
        }

        return removed
    }

    fun totalPrice(): Long = itemList.sumOf(CartItem::linePrice)

    fun totalQuantity(): Int = itemList.sumOf(CartItem::quantity)

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
