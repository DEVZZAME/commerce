package zzame.commerce

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import zzame.commerce.product.entity.Product
import zzame.commerce.product.entity.ProductStatus

class ProductEntityTests {

    @Test
    fun `product create normalizes values and sets default status`() {
        val product = Product.create(
            sellerId = 1L,
            name = "  Keyboard  ",
            price = 49000L,
            description = "  Mechanical keyboard  ",
        )

        assertThat(product.id).isZero()
        assertThat(product.sellerId).isEqualTo(1L)
        assertThat(product.name).isEqualTo("Keyboard")
        assertThat(product.price).isEqualTo(49000L)
        assertThat(product.description).isEqualTo("Mechanical keyboard")
        assertThat(product.status).isEqualTo(ProductStatus.ON_SALE)
    }

    @Test
    fun `product can transition to sold out`() {
        val product = Product.create(
            sellerId = 1L,
            name = "Keyboard",
            price = 49000L,
            description = null,
        )

        product.markSoldOut()

        assertThat(product.status).isEqualTo(ProductStatus.SOLD_OUT)
    }

    @Test
    fun `product create rejects blank name`() {
        assertThatThrownBy {
            Product.create(
                sellerId = 1L,
                name = "   ",
                price = 49000L,
                description = null,
            )
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Product name must not be blank")
    }
}
