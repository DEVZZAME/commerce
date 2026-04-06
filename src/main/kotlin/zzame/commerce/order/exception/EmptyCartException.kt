package zzame.commerce.order.exception

import zzame.commerce.common.exception.CommerceException
import zzame.commerce.common.exception.ErrorCode

class EmptyCartException(customerId: Long) : CommerceException(
    errorCode = ErrorCode.EMPTY_CART,
    message = "Cart is empty. customerId=$customerId",
)
