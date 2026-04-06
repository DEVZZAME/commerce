package zzame.commerce.product.service

import zzame.commerce.common.response.PageResponse
import zzame.commerce.product.dto.ProductCreateRequest
import zzame.commerce.product.dto.ProductResponse
import zzame.commerce.product.dto.ProductSearchRequest
import zzame.commerce.product.dto.ProductSummaryResponse
import zzame.commerce.product.entity.Product
import zzame.commerce.product.exception.ProductNotFoundException
import zzame.commerce.product.exception.SellerNotFoundException
import zzame.commerce.product.repository.ProductRepository
import zzame.commerce.product.repository.SellerRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val sellerRepository: SellerRepository,
) {
    @Transactional(readOnly = true)
    fun getProducts(searchRequest: ProductSearchRequest): PageResponse<ProductSummaryResponse> = productRepository.search(
        condition = searchRequest.toCondition(),
        pageable = searchRequest.toPageable(),
    ).let { PageResponse.from(it) }

    @Transactional(readOnly = true)
    fun getProduct(productId: Long): ProductResponse = productRepository.findDetailById(productId)
        ?.let(ProductResponse::from)
        ?: throw ProductNotFoundException(productId)

    @Transactional
    fun createProduct(request: ProductCreateRequest): ProductResponse {
        val seller = sellerRepository.findById(request.sellerId)
            .orElseThrow { SellerNotFoundException(request.sellerId) }
        val product = Product.create(
            seller = seller,
            name = request.name,
            price = request.price,
            description = request.normalizedDescription,
        )

        return productRepository.save(product)
            .let { productRepository.findDetailById(it.id) ?: it }
            .let(ProductResponse::from)
    }
}
