package zzame.commerce.product.dto

import zzame.commerce.product.entity.ProductStatus

data class ProductSearchCondition(
    val keyword: String? = null,
    val sellerId: Long? = null,
    val status: ProductStatus? = null,
    val minPrice: Long? = null,
    val maxPrice: Long? = null,
)
