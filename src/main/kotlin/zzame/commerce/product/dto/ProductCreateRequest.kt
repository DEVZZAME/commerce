package zzame.commerce.product.dto

import zzame.commerce.common.util.trimToNull

data class ProductCreateRequest(
    val sellerId: Long,
    val name: String,
    val price: Long,
    val description: String?,
) {
    val normalizedDescription: String?
        get() = description.trimToNull()
}
