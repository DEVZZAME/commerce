package zzame.commerce.common.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val code: String,
    val message: String,
) {
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "COMMON-400", "Invalid request input"),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT-404", "Product not found"),
    SELLER_NOT_FOUND(HttpStatus.NOT_FOUND, "SELLER-404", "Seller not found"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-500", "Internal server error"),
}
