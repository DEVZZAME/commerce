package zzame.commerce.product.dto

import zzame.commerce.product.entity.ProductOption

data class ProductOptionResponse(
    val id: Long,
    val name: String,
    val additionalPrice: Long,
    val stockQuantity: Int,
    val description: String?,
    val optionOrder: Int,
) {
    companion object {
        fun from(option: ProductOption): ProductOptionResponse = ProductOptionResponse(
            id = option.id,
            name = option.name,
            additionalPrice = option.additionalPrice,
            stockQuantity = option.stockQuantity,
            description = option.description,
            optionOrder = option.optionOrder,
        )
    }
}
