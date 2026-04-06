package zzame.commerce.cart.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Positive

data class CartAddItemRequest(
    @field:Positive
    val productId: Long,

    @field:Positive
    val productOptionId: Long,

    @field:Min(1)
    val quantity: Int,
)
