package zzame.commerce.product.controller

import zzame.commerce.common.response.PageResponse
import zzame.commerce.common.response.RestResponse
import zzame.commerce.product.dto.ProductCreateRequest
import zzame.commerce.product.dto.ProductResponse
import zzame.commerce.product.dto.ProductSearchRequest
import zzame.commerce.product.dto.ProductSummaryResponse
import zzame.commerce.product.service.ProductService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/products")
class ProductController(
    private val productService: ProductService,
) {
    @GetMapping
    fun getProducts(
        @Valid @ModelAttribute searchRequest: ProductSearchRequest,
    ): RestResponse<PageResponse<ProductSummaryResponse>> = RestResponse.success(
        data = productService.getProducts(searchRequest),
        message = "Products fetched successfully",
    )

    @GetMapping("/{productId}")
    fun getProduct(@PathVariable productId: Long): RestResponse<ProductResponse> = RestResponse.success(
        data = productService.getProduct(productId),
        message = "Product fetched successfully",
    )

    @PostMapping
    fun createProduct(
        @Valid @RequestBody request: ProductCreateRequest,
    ): ResponseEntity<RestResponse<ProductResponse>> = ResponseEntity.status(HttpStatus.CREATED)
        .body(
            RestResponse.success(
                data = productService.createProduct(request),
                message = "Product created successfully",
            ),
        )
}
