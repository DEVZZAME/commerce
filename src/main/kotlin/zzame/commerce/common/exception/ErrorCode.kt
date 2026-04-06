package zzame.commerce.common.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val code: String,
    val message: String,
) {
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "COMMON-400", "Invalid request input"),
    EMPTY_CART(HttpStatus.BAD_REQUEST, "CART-400", "Cart is empty"),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT-404", "Product not found"),
    PRODUCT_OPTION_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT_OPTION-404", "Product option not found"),
    SELLER_NOT_FOUND(HttpStatus.NOT_FOUND, "SELLER-404", "Seller not found"),
    CART_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "CART_ITEM-404", "Cart item not found"),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER-404", "Order not found"),
    INSUFFICIENT_STOCK(HttpStatus.CONFLICT, "PRODUCT_OPTION-409", "Insufficient stock"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-500", "Internal server error"),
}
