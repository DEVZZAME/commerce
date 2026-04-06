package zzame.commerce.settlement.service

import zzame.commerce.order.entity.PaymentStatus
import zzame.commerce.order.repository.OrderItemRepository
import zzame.commerce.product.exception.SellerNotFoundException
import zzame.commerce.product.repository.SellerRepository
import zzame.commerce.settlement.entity.Settlement
import zzame.commerce.settlement.repository.SettlementRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class SettlementService(
    private val orderItemRepository: OrderItemRepository,
    private val sellerRepository: SellerRepository,
    private val settlementRepository: SettlementRepository,
) {
    companion object {
        private const val ADVERTISING_COST_RATE_PERCENT = 3L
    }

    @Transactional
    fun calculateWeeklySettlements(weekStartDate: LocalDate): List<Settlement> {
        val weekEndDate = weekStartDate.plusDays(6)
        val summaries = orderItemRepository.summarizeWeeklySalesBySeller(
            paymentStatus = PaymentStatus.PAID,
            startDateTime = weekStartDate.atStartOfDay(),
            endDateTime = weekEndDate.plusDays(1).atStartOfDay(),
        )

        return summaries.map { summary ->
            val seller = sellerRepository.findById(summary.sellerId)
                .orElseThrow { SellerNotFoundException(summary.sellerId) }
            val advertisingCost = calculateAdvertisingCost(summary.grossSales)
            val payoutAmount = calculatePayoutAmount(summary.grossSales, advertisingCost)

            val settlement = settlementRepository.findBySellerIdAndStartDateAndEndDate(
                sellerId = seller.id,
                startDate = weekStartDate,
                endDate = weekEndDate,
            ) ?: Settlement.create(
                seller = seller,
                startDate = weekStartDate,
                endDate = weekEndDate,
                orderCount = summary.orderCount,
                totalQuantity = summary.totalQuantity,
                grossSales = summary.grossSales,
                advertisingCost = advertisingCost,
                payoutAmount = payoutAmount,
            )

            settlement.updateAmounts(
                orderCount = summary.orderCount,
                totalQuantity = summary.totalQuantity,
                grossSales = summary.grossSales,
                advertisingCost = advertisingCost,
                payoutAmount = payoutAmount,
            )

            settlementRepository.save(settlement)
        }
    }

    fun calculateAdvertisingCost(grossSales: Long): Long = grossSales * ADVERTISING_COST_RATE_PERCENT / 100

    fun calculatePayoutAmount(grossSales: Long, advertisingCost: Long): Long = grossSales - advertisingCost
}
