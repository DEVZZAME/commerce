package zzame.commerce.cart.exception

import zzame.commerce.common.exception.CommerceException
import zzame.commerce.common.exception.ErrorCode

class CartItemNotFoundException(cartItemId: Long) : CommerceException(
    errorCode = ErrorCode.CART_ITEM_NOT_FOUND,
    message = "Cart item not found. id=$cartItemId",
)
