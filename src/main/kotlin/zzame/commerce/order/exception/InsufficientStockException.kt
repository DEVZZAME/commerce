package zzame.commerce.order.exception

import zzame.commerce.common.exception.CommerceException
import zzame.commerce.common.exception.ErrorCode

class InsufficientStockException(
    productOptionId: Long,
    currentStock: Int,
    requestedQuantity: Int,
) : CommerceException(
    errorCode = ErrorCode.INSUFFICIENT_STOCK,
    message = "Insufficient stock. productOptionId=$productOptionId, currentStock=$currentStock, requestedQuantity=$requestedQuantity",
)
