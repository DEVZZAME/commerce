package zzame.commerce.product.entity

import zzame.commerce.common.util.trimToNull
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
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

    @Column(nullable = false, length = 100)
    var name: String = "",

    @Column(nullable = false)
    var price: Long = 0L,

    @Column(columnDefinition = "TEXT")
    var description: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var status: ProductStatus = ProductStatus.ON_SALE,

    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL], orphanRemoval = true)
    private val optionList: MutableList<ProductOption> = mutableListOf(),

    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    lateinit var seller: Seller

    val options: List<ProductOption>
        get() = optionList.sortedBy(ProductOption::optionOrder)

    companion object {
        fun create(
            seller: Seller,
            name: String,
            price: Long,
            description: String?,
        ): Product = Product(
            name = normalizeName(name),
            price = requirePositivePrice(price),
            description = description.trimToNull(),
        ).apply {
            this.seller = seller
        }.also(seller::addProduct)

        private fun normalizeName(name: String): String = name.trim()
            .takeIf { it.isNotEmpty() }
            ?: throw IllegalArgumentException("Product name must not be blank")

        private fun requirePositivePrice(price: Long): Long = price
            .takeIf { it > 0 }
            ?: throw IllegalArgumentException("price must be greater than 0")
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

    fun addOption(
        name: String,
        additionalPrice: Long,
        stockQuantity: Int,
        description: String? = null,
    ): ProductOption {
        val productOption = ProductOption.create(
            product = this,
            name = name,
            additionalPrice = additionalPrice,
            stockQuantity = stockQuantity,
            description = description,
            optionOrder = optionList.size,
        )

        optionList.add(productOption)
        updatedAt = LocalDateTime.now()

        return productOption
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
