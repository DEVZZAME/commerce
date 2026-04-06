package zzame.commerce.product.entity

import zzame.commerce.common.util.trimToNull

data class Product(
    val id: Long = 0L,
    val sellerId: Long,
    val name: String,
    val price: Long,
    val description: String?,
) {
    companion object {
        fun create(
            sellerId: Long,
            name: String,
            price: Long,
            description: String?,
        ): Product = Product(
            sellerId = sellerId,
            name = name.trim(),
            price = price,
            description = description.trimToNull(),
        )
    }
}
