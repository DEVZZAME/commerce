package zzame.commerce.order.dto

import zzame.commerce.order.entity.OrderItem

data class OrderItemResponse(
    val id: Long,
    val productId: Long,
    val productOptionId: Long,
    val productName: String,
    val optionName: String,
    val sellerName: String,
    val quantity: Int,
    val unitPrice: Long,
    val linePrice: Long,
) {
    companion object {
        fun from(orderItem: OrderItem): OrderItemResponse = OrderItemResponse(
            id = orderItem.id,
            productId = orderItem.product.id,
            productOptionId = orderItem.productOption.id,
            productName = orderItem.productName,
            optionName = orderItem.optionName,
            sellerName = orderItem.sellerName,
            quantity = orderItem.quantity,
            unitPrice = orderItem.unitPrice,
            linePrice = orderItem.linePrice,
        )
    }
}
