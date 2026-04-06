package zzame.commerce.order.dto

import zzame.commerce.order.entity.OrderStatus
import zzame.commerce.order.entity.PaymentStatus
import java.time.LocalDateTime

data class OrderSearchCondition(
    val customerId: Long? = null,
    val sellerId: Long? = null,
    val orderStatus: OrderStatus? = null,
    val paymentStatus: PaymentStatus? = null,
    val orderedFrom: LocalDateTime? = null,
    val orderedTo: LocalDateTime? = null,
)
