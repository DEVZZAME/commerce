package zzame.commerce.settlement.dto

data class SellerWeeklySettlementSummary(
    val sellerId: Long,
    val sellerName: String,
    val orderCount: Long,
    val totalQuantity: Long,
    val grossSales: Long,
)
