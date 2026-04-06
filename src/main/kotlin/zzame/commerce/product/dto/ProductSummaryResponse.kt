package zzame.commerce.product.dto

import zzame.commerce.product.entity.ProductStatus
import java.time.LocalDateTime

data class ProductSummaryResponse(
    val id: Long,
    val sellerId: Long,
    val sellerName: String,
    val name: String,
    val price: Long,
    val status: ProductStatus,
    val createdAt: LocalDateTime,
)
