package zzame.commerce.order.repository

import zzame.commerce.order.entity.OrderItem
import zzame.commerce.order.entity.PaymentStatus
import zzame.commerce.settlement.dto.SellerWeeklySettlementSummary
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface OrderItemRepository : JpaRepository<OrderItem, Long> {
    @Query(
        """
        select new zzame.commerce.settlement.dto.SellerWeeklySettlementSummary(
            oi.product.seller.id,
            oi.sellerName,
            count(distinct o.id),
            sum(oi.quantity),
            sum(oi.linePrice)
        )
        from OrderItem oi
        join oi.order o
        where o.paymentStatus = :paymentStatus
          and o.orderedAt >= :startDateTime
          and o.orderedAt < :endDateTime
        group by oi.product.seller.id, oi.sellerName
        """,
    )
    fun summarizeWeeklySalesBySeller(
        @Param("paymentStatus") paymentStatus: PaymentStatus,
        @Param("startDateTime") startDateTime: LocalDateTime,
        @Param("endDateTime") endDateTime: LocalDateTime,
    ): List<SellerWeeklySettlementSummary>
}
