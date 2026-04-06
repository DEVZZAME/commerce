package zzame.commerce

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import zzame.commerce.product.dto.ProductCreateRequest
import zzame.commerce.product.dto.ProductResponse

class ProductDtoTests {

    @Test
    fun `create request normalizes blank description to null`() {
        val request = ProductCreateRequest(
            sellerId = 1L,
            name = "Keyboard",
            price = 50000L,
            description = "   ",
        )

        assertThat(request.normalizedDescription).isNull()
    }

    @Test
    fun `response factory trims description safely`() {
        val response = ProductResponse.of(
            id = 1L,
            sellerId = 1L,
            name = "Keyboard",
            price = 50000L,
            description = "  mechanical keyboard  ",
        )

        assertThat(response.description).isEqualTo("mechanical keyboard")
        assertThat(response.descriptionOrEmpty).isEqualTo("mechanical keyboard")
    }

    @Test
    fun `response exposes empty string when description is null`() {
        val response = ProductResponse.of(
            id = 1L,
            sellerId = 1L,
            name = "Keyboard",
            price = 50000L,
            description = null,
        )

        assertThat(response.description).isNull()
        assertThat(response.descriptionOrEmpty).isEmpty()
    }
}
