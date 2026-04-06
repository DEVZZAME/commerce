package zzame.commerce

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.data.domain.PageImpl
import zzame.commerce.product.dto.ProductPopularityResponse
import zzame.commerce.product.dto.ProductSearchCondition
import zzame.commerce.product.dto.ProductSummaryResponse
import zzame.commerce.product.entity.ProductStatus
import zzame.commerce.product.repository.ProductRepository
import zzame.commerce.product.repository.SellerRepository
import zzame.commerce.product.service.ProductService

class ProductServiceUnitTests {

    private val productRepository: ProductRepository = mock()
    private val sellerRepository: SellerRepository = mock()

    private val productService = ProductService(
        productRepository = productRepository,
        sellerRepository = sellerRepository,
    )

    @Test
    fun `get popular products returns aggregated result when paid order data exists`() {
        whenever(productRepository.findPopularProducts(3)).thenReturn(
            listOf(
                ProductPopularityResponse(
                    id = 1L,
                    sellerId = 10L,
                    sellerName = "셀러",
                    name = "인기 키보드",
                    price = 99000L,
                    status = ProductStatus.ON_SALE,
                    totalSoldQuantity = 12L,
                ),
            ),
        )

        val result = productService.getPopularProducts(3)

        assertThat(result).hasSize(1)
        assertThat(result.first().name).isEqualTo("인기 키보드")
        assertThat(result.first().totalSoldQuantity).isEqualTo(12L)
    }

    @Test
    fun `get popular products falls back to latest products when there is no paid order data`() {
        whenever(productRepository.findPopularProducts(2)).thenReturn(emptyList())
        whenever(productRepository.search(any<ProductSearchCondition>(), any())).thenReturn(
            PageImpl(
                listOf(
                    ProductSummaryResponse(
                        id = 2L,
                        sellerId = 20L,
                        sellerName = "셀러 둘",
                        name = "신규 마우스",
                        price = 45000L,
                        status = ProductStatus.ON_SALE,
                        createdAt = java.time.LocalDateTime.now(),
                    ),
                ),
            ),
        )

        val result = productService.getPopularProducts(2)

        assertThat(result).hasSize(1)
        assertThat(result.first().name).isEqualTo("신규 마우스")
        assertThat(result.first().totalSoldQuantity).isZero()
    }
}
