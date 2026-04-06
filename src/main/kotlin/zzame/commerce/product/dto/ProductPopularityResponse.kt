package zzame.commerce.product.dto

import zzame.commerce.product.entity.ProductStatus

data class ProductPopularityResponse(
    val id: Long,
    val sellerId: Long,
    val sellerName: String,
    val name: String,
    val price: Long,
    val status: ProductStatus,
    val totalSoldQuantity: Long,
) {
    companion object {
        fun from(summary: ProductSummaryResponse): ProductPopularityResponse = ProductPopularityResponse(
            id = summary.id,
            sellerId = summary.sellerId,
            sellerName = summary.sellerName,
            name = summary.name,
            price = summary.price,
            status = summary.status,
            totalSoldQuantity = 0L,
        )
    }
}
