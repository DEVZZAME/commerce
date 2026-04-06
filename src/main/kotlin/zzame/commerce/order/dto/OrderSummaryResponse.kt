package zzame.commerce.order.dto

import zzame.commerce.order.entity.OrderStatus
import zzame.commerce.order.entity.PaymentStatus
import java.time.LocalDateTime

data class OrderSummaryResponse(
    val id: Long,
    val orderNumber: String,
    val customerId: Long,
    val recipientName: String,
    val status: OrderStatus,
    val paymentStatus: PaymentStatus,
    val totalQuantity: Int,
    val totalPrice: Long,
    val orderedAt: LocalDateTime,
)
