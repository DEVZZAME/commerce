package zzame.commerce.order.dto

import zzame.commerce.order.entity.Order
import zzame.commerce.order.entity.OrderStatus
import zzame.commerce.order.entity.PaymentStatus
import java.time.LocalDateTime

data class OrderResponse(
    val id: Long,
    val orderNumber: String,
    val customerId: Long,
    val recipientName: String,
    val phone: String,
    val address: String,
    val detailAddress: String,
    val deliveryRequest: String?,
    val status: OrderStatus,
    val paymentStatus: PaymentStatus,
    val totalQuantity: Int,
    val totalPrice: Long,
    val orderedAt: LocalDateTime,
    val items: List<OrderItemResponse>,
) {
    companion object {
        fun from(order: Order): OrderResponse = OrderResponse(
            id = order.id,
            orderNumber = order.orderNumber,
            customerId = order.customerId,
            recipientName = order.recipientName,
            phone = order.phone,
            address = order.address,
            detailAddress = order.detailAddress,
            deliveryRequest = order.deliveryRequest,
            status = order.status,
            paymentStatus = order.paymentStatus,
            totalQuantity = order.totalQuantity,
            totalPrice = order.totalPrice,
            orderedAt = order.orderedAt,
            items = order.items.map(OrderItemResponse::from),
        )
    }
}
