package zzame.commerce.product.dto

data class ProductCreateRequest(
    val sellerId: Long,
    val name: String,
    val price: Long,
    val description: String?,
)
