package zzame.commerce.product.dto

import zzame.commerce.common.util.trimToNull
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size

data class ProductCreateRequest(
    @field:Positive(message = "sellerId must be greater than 0")
    val sellerId: Long,
    @field:NotBlank(message = "name must not be blank")
    @field:Size(max = 100, message = "name must be 100 characters or fewer")
    val name: String,
    @field:Positive(message = "price must be greater than 0")
    val price: Long,
    @field:Size(max = 1000, message = "description must be 1000 characters or fewer")
    val description: String?,
) {
    val normalizedDescription: String?
        get() = description.trimToNull()
}
