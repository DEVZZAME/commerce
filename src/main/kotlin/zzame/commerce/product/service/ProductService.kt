package zzame.commerce.product.service

import zzame.commerce.product.dto.ProductCreateRequest
import zzame.commerce.product.dto.ProductResponse
import zzame.commerce.product.entity.Product
import zzame.commerce.product.exception.ProductNotFoundException
import zzame.commerce.product.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
) {
    fun getProducts(): List<ProductResponse> = productRepository.findAll()
        .map(ProductResponse::from)

    fun getProduct(productId: Long): ProductResponse = productRepository.findById(productId)
        ?.let(ProductResponse::from)
        ?: throw ProductNotFoundException(productId)

    fun createProduct(request: ProductCreateRequest): ProductResponse {
        val product = Product.create(
            sellerId = request.sellerId,
            name = request.name,
            price = request.price,
            description = request.normalizedDescription,
        )

        return productRepository.save(product)
            .let(ProductResponse::from)
    }
}
