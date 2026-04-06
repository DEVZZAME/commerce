package zzame.commerce.order.exception

import zzame.commerce.common.exception.CommerceException
import zzame.commerce.common.exception.ErrorCode

class OrderNotFoundException(orderId: Long) : CommerceException(
    errorCode = ErrorCode.ORDER_NOT_FOUND,
    message = "Order not found. id=$orderId",
)
