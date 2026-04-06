package zzame.commerce.cart.dto

import zzame.commerce.cart.entity.CartItem

data class CartItemResponse(
    val id: Long,
    val productId: Long,
    val productOptionId: Long,
    val productName: String,
    val optionName: String,
    val sellerName: String?,
    val quantity: Int,
    val unitPrice: Long,
    val linePrice: Long,
) {
    companion object {
        fun from(cartItem: CartItem): CartItemResponse = CartItemResponse(
            id = cartItem.id,
            productId = cartItem.product.id,
            productOptionId = cartItem.productOption.id,
            productName = cartItem.product.name,
            optionName = cartItem.productOption.name,
            sellerName = cartItem.product.seller.name,
            quantity = cartItem.quantity,
            unitPrice = cartItem.unitPrice,
            linePrice = cartItem.linePrice(),
        )
    }
}
