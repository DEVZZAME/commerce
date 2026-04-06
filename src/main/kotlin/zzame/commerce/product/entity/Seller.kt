package zzame.commerce.product.entity

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
@Table(name = "sellers")
class Seller protected constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    @Column(nullable = false, length = 100)
    var name: String = "",

    @Column(nullable = false, unique = true, length = 150)
    var email: String = "",

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var status: SellerStatus = SellerStatus.ACTIVE,

    @OneToMany(mappedBy = "seller", cascade = [CascadeType.ALL])
    private val productList: MutableList<Product> = mutableListOf(),

    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    val products: List<Product>
        get() = productList.toList()

    companion object {
        fun create(name: String, email: String): Seller = Seller(
            name = normalizeName(name),
            email = normalizeEmail(email),
        )

        private fun normalizeName(name: String): String = name.trim()
            .takeIf { it.isNotEmpty() }
            ?: throw IllegalArgumentException("Seller name must not be blank")

        private fun normalizeEmail(email: String): String = email.trim()
            .lowercase()
            .takeIf { it.isNotEmpty() }
            ?: throw IllegalArgumentException("Seller email must not be blank")
    }

    fun activate() {
        status = SellerStatus.ACTIVE
        updatedAt = LocalDateTime.now()
    }

    fun deactivate() {
        status = SellerStatus.INACTIVE
        updatedAt = LocalDateTime.now()
    }

    internal fun addProduct(product: Product) {
        if (!productList.contains(product)) {
            productList.add(product)
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
