package zzame.commerce

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import zzame.commerce.product.dto.ProductResponse
import zzame.commerce.product.service.ProductCollectionService

class ProductCollectionServiceTests {

    @Test
    fun `filters and sorts products with kotlin collection functions`() {
        val products = listOf(
            ProductResponse.of(1L, 1L, "Keyboard", 50000L, null),
            ProductResponse.of(2L, 1L, "Mouse", 30000L, null),
            ProductResponse.of(3L, 2L, "Monitor", 200000L, null),
        )

        val productNames = ProductCollectionService.findProductNamesOverPrice(
            products = products,
            minimumPrice = 50000L,
        )

        assertThat(productNames).containsExactly("Monitor", "Keyboard")
    }

    @Test
    fun `counts products by seller`() {
        val products = listOf(
            ProductResponse.of(1L, 1L, "Keyboard", 50000L, null),
            ProductResponse.of(2L, 1L, "Mouse", 30000L, null),
            ProductResponse.of(3L, 2L, "Monitor", 200000L, null),
        )

        val countBySeller = ProductCollectionService.countProductsBySeller(products)

        assertThat(countBySeller).containsExactlyInAnyOrderEntriesOf(
            mapOf(
                1L to 2,
                2L to 1,
            ),
        )
    }
}
