package zzame.commerce.order.dto

import zzame.commerce.common.util.trimToNull
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive

data class OrderCreateRequest(
    @field:Positive
    val customerId: Long,

    @field:NotBlank
    val recipientName: String,

    @field:NotBlank
    val phone: String,

    @field:NotBlank
    val address: String,

    @field:NotBlank
    val detailAddress: String,

    val deliveryRequest: String? = null,
) {
    val normalizedDeliveryRequest: String?
        get() = deliveryRequest.trimToNull()
}
