package zzame.commerce.cart.dto

import zzame.commerce.cart.entity.Cart

data class CartResponse(
    val customerId: Long,
    val items: List<CartItemResponse>,
    val totalQuantity: Int,
    val totalPrice: Long,
) {
    companion object {
        fun from(cart: Cart): CartResponse = CartResponse(
            customerId = cart.customerId,
            items = cart.items.map(CartItemResponse::from),
            totalQuantity = cart.totalQuantity(),
            totalPrice = cart.totalPrice(),
        )

        fun empty(customerId: Long): CartResponse = CartResponse(
            customerId = customerId,
            items = emptyList(),
            totalQuantity = 0,
            totalPrice = 0L,
        )
    }
}
