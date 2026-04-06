package zzame.commerce.product.entity

import zzame.commerce.common.util.trimToNull
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "products")
class Product protected constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    @Column(nullable = false)
    var sellerId: Long = 0L,

    @Column(nullable = false, length = 100)
    var name: String = "",

    @Column(nullable = false)
    var price: Long = 0L,

    @Column(columnDefinition = "TEXT")
    var description: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var status: ProductStatus = ProductStatus.ON_SALE,

    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    companion object {
        fun create(
            sellerId: Long,
            name: String,
            price: Long,
            description: String?,
        ): Product = Product(
            sellerId = requirePositiveSellerId(sellerId),
            name = normalizeName(name),
            price = requirePositivePrice(price),
            description = description.trimToNull(),
        )

        private fun normalizeName(name: String): String = name.trim()
            .takeIf { it.isNotEmpty() }
            ?: throw IllegalArgumentException("Product name must not be blank")

        private fun requirePositiveSellerId(sellerId: Long): Long = sellerId
            .takeIf { it > 0 }
            ?: throw IllegalArgumentException("sellerId must be greater than 0")

        private fun requirePositivePrice(price: Long): Long = price
            .takeIf { it > 0 }
            ?: throw IllegalArgumentException("price must be greater than 0")
    }

    fun assignId(newId: Long): Product {
        require(id == 0L) { "Product id is already assigned" }
        id = newId
        return this
    }

    fun updateDetails(
        name: String,
        price: Long,
        description: String?,
    ) {
        this.name = normalizeName(name)
        this.price = requirePositivePrice(price)
        this.description = description.trimToNull()
        this.updatedAt = LocalDateTime.now()
    }

    fun markSoldOut() {
        status = ProductStatus.SOLD_OUT
        updatedAt = LocalDateTime.now()
    }

    fun hide() {
        status = ProductStatus.HIDDEN
        updatedAt = LocalDateTime.now()
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
