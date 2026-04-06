package zzame.commerce.product.dto

data class ProductResponse(
    val id: Long,
    val sellerId: Long,
    val name: String,
    val price: Long,
    val description: String?,
) {
    val descriptionOrEmpty: String
        get() = description.orEmpty()

    companion object {
        fun of(
            id: Long,
            sellerId: Long,
            name: String,
            price: Long,
            description: String?,
        ): ProductResponse = ProductResponse(
            id = id,
            sellerId = sellerId,
            name = name,
            price = price,
            description = description?.trim()?.takeIf { it.isNotEmpty() },
        )
    }
}
