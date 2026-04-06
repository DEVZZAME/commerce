package zzame.commerce.product.service

import zzame.commerce.product.dto.ProductResponse

object ProductCollectionService {

    fun findProductNamesOverPrice(
        products: List<ProductResponse>,
        minimumPrice: Long,
    ): List<String> = products
        .filter { it.price >= minimumPrice }
        .sortedByDescending { it.price }
        .map { it.name }

    fun countProductsBySeller(products: List<ProductResponse>): Map<Long, Int> = products
        .groupingBy { it.sellerId }
        .eachCount()
}
