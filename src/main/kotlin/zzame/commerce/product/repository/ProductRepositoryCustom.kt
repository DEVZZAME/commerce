package zzame.commerce.product.repository

import zzame.commerce.product.dto.ProductSearchCondition
import zzame.commerce.product.dto.ProductSummaryResponse
import zzame.commerce.product.entity.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ProductRepositoryCustom {
    fun search(condition: ProductSearchCondition, pageable: Pageable): Page<ProductSummaryResponse>
    fun findDetailById(productId: Long): Product?
}
