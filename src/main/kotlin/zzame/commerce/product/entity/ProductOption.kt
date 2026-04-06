package zzame.commerce.product.entity

import zzame.commerce.common.util.trimToNull
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
@Table(name = "product_options")
class ProductOption protected constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    @Column(nullable = false, length = 100)
    var name: String = "",

    @Column(nullable = false)
    var additionalPrice: Long = 0L,

    @Column(nullable = false)
    var stockQuantity: Int = 0,

    @Column(length = 500)
    var description: String? = null,

    @Column(nullable = false)
    var optionOrder: Int = 0,

    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    lateinit var product: Product

    companion object {
        fun create(
            product: Product,
            name: String,
            additionalPrice: Long,
            stockQuantity: Int,
            description: String?,
            optionOrder: Int,
        ): ProductOption = ProductOption(
            name = normalizeName(name),
            additionalPrice = requireNonNegativeAdditionalPrice(additionalPrice),
            stockQuantity = requireNonNegativeStock(stockQuantity),
            description = description.trimToNull(),
            optionOrder = requireNonNegativeOrder(optionOrder),
        ).apply {
            this.product = product
        }

        private fun normalizeName(name: String): String = name.trim()
            .takeIf { it.isNotEmpty() }
            ?: throw IllegalArgumentException("Option name must not be blank")

        private fun requireNonNegativeAdditionalPrice(additionalPrice: Long): Long = additionalPrice
            .takeIf { it >= 0 }
            ?: throw IllegalArgumentException("additionalPrice must be 0 or greater")

        private fun requireNonNegativeStock(stockQuantity: Int): Int = stockQuantity
            .takeIf { it >= 0 }
            ?: throw IllegalArgumentException("stockQuantity must be 0 or greater")

        private fun requireNonNegativeOrder(optionOrder: Int): Int = optionOrder
            .takeIf { it >= 0 }
            ?: throw IllegalArgumentException("optionOrder must be 0 or greater")
    }

    fun updateStock(stockQuantity: Int) {
        this.stockQuantity = requireNonNegativeStock(stockQuantity)
        updatedAt = LocalDateTime.now()
    }

    fun deductStock(quantity: Int) {
        val validQuantity = requireNonNegativeStock(quantity)

        if (validQuantity == 0) {
            return
        }

        if (stockQuantity < validQuantity) {
            throw IllegalStateException("stockQuantity is insufficient")
        }

        stockQuantity -= validQuantity
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
