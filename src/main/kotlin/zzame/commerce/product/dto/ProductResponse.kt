package zzame.commerce.product.dto

data class ProductResponse(
    val id: Long,
    val sellerId: Long,
    val name: String,
    val price: Long,
    val description: String?,
)
