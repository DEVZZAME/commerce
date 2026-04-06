package zzame.commerce.settlement.entity

import zzame.commerce.product.entity.Seller
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
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "settlements")
class Settlement protected constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    @Column(nullable = false)
    var startDate: LocalDate = LocalDate.now(),

    @Column(nullable = false)
    var endDate: LocalDate = LocalDate.now(),

    @Column(nullable = false)
    var orderCount: Long = 0L,

    @Column(nullable = false)
    var totalQuantity: Long = 0L,

    @Column(nullable = false)
    var grossSales: Long = 0L,

    @Column(nullable = false)
    var advertisingCost: Long = 0L,

    @Column(nullable = false)
    var payoutAmount: Long = 0L,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var status: SettlementStatus = SettlementStatus.PENDING,

    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    lateinit var seller: Seller

    companion object {
        fun create(
            seller: Seller,
            startDate: LocalDate,
            endDate: LocalDate,
            orderCount: Long,
            totalQuantity: Long,
            grossSales: Long,
            advertisingCost: Long,
            payoutAmount: Long,
        ): Settlement = Settlement(
            startDate = startDate,
            endDate = endDate,
            orderCount = orderCount,
            totalQuantity = totalQuantity,
            grossSales = grossSales,
            advertisingCost = advertisingCost,
            payoutAmount = payoutAmount,
        ).apply {
            this.seller = seller
        }
    }

    fun updateAmounts(
        orderCount: Long,
        totalQuantity: Long,
        grossSales: Long,
        advertisingCost: Long,
        payoutAmount: Long,
    ) {
        this.orderCount = orderCount
        this.totalQuantity = totalQuantity
        this.grossSales = grossSales
        this.advertisingCost = advertisingCost
        this.payoutAmount = payoutAmount
        updatedAt = LocalDateTime.now()
    }

    fun markCompleted() {
        status = SettlementStatus.COMPLETED
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
