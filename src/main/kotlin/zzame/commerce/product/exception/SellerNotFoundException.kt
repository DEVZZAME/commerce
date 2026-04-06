package zzame.commerce.product.exception

import zzame.commerce.common.exception.CommerceException
import zzame.commerce.common.exception.ErrorCode

class SellerNotFoundException(sellerId: Long) : CommerceException(
    errorCode = ErrorCode.SELLER_NOT_FOUND,
    message = "Seller not found. id=$sellerId",
)
