package zzame.commerce.cart.exception

import zzame.commerce.common.exception.CommerceException
import zzame.commerce.common.exception.ErrorCode

class ProductOptionNotFoundException(productOptionId: Long) : CommerceException(
    errorCode = ErrorCode.PRODUCT_OPTION_NOT_FOUND,
    message = "Product option not found. id=$productOptionId",
)
