package zzame.commerce.product.dto

import zzame.commerce.product.entity.ProductStatus
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Positive
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

data class ProductSearchRequest(
    val keyword: String? = null,
    val sellerId: Long? = null,
    val status: ProductStatus? = null,
    val minPrice: Long? = null,
    val maxPrice: Long? = null,
    @field:Min(0)
    val page: Int = 0,
    @field:Positive
    @field:Max(100)
    val size: Int = 20,
    val sortBy: String = "createdAt",
    val direction: Sort.Direction = Sort.Direction.DESC,
) {
    fun toCondition(): ProductSearchCondition = ProductSearchCondition(
        keyword = keyword?.trim()?.takeIf { it.isNotEmpty() },
        sellerId = sellerId,
        status = status,
        minPrice = minPrice,
        maxPrice = maxPrice,
    )

    fun toPageable(): Pageable = PageRequest.of(
        page,
        size,
        Sort.by(direction, sortBy),
    )
}
