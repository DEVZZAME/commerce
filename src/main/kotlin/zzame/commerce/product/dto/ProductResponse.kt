package zzame.commerce.product.dto

import zzame.commerce.common.util.trimToNull
import zzame.commerce.product.entity.Product
import zzame.commerce.product.entity.ProductStatus

data class ProductResponse(
    val id: Long,
    val sellerId: Long,
    val sellerName: String?,
    val name: String,
    val price: Long,
    val description: String?,
    val status: ProductStatus,
    val options: List<ProductOptionResponse> = emptyList(),
) {
    val descriptionOrEmpty: String
        get() = description.orEmpty()

    companion object {
        fun from(product: Product): ProductResponse = ProductResponse(
            id = product.id,
            sellerId = product.seller.id,
            sellerName = product.seller.name,
            name = product.name,
            price = product.price,
            description = product.description.trimToNull(),
            status = product.status,
            options = product.options.map(ProductOptionResponse::from),
        )

        fun of(
            id: Long,
            sellerId: Long,
            name: String,
            price: Long,
            description: String?,
            sellerName: String? = null,
            status: ProductStatus = ProductStatus.ON_SALE,
            options: List<ProductOptionResponse> = emptyList(),
        ): ProductResponse = ProductResponse(
            id = id,
            sellerId = sellerId,
            sellerName = sellerName,
            name = name,
            price = price,
            description = description.trimToNull(),
            status = status,
            options = options,
        )
    }
}
