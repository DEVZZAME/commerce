package zzame.commerce.product.exception

import zzame.commerce.common.exception.CommerceException
import zzame.commerce.common.exception.ErrorCode

class ProductNotFoundException(productId: Long) : CommerceException(
    errorCode = ErrorCode.PRODUCT_NOT_FOUND,
    message = "Product not found. id=$productId",
)
