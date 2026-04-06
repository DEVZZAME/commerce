package zzame.commerce

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import zzame.commerce.product.entity.Product
import zzame.commerce.product.entity.ProductStatus
import zzame.commerce.product.entity.Seller

class ProductEntityTests {

    @Test
    fun `product create normalizes values and sets default status`() {
        val seller = Seller.create(
            name = "Seller A",
            email = "seller-a@example.com",
        )
        val product = Product.create(
            seller = seller,
            name = "  Keyboard  ",
            price = 49000L,
            description = "  Mechanical keyboard  ",
        )

        assertThat(product.id).isZero()
        assertThat(product.seller).isEqualTo(seller)
        assertThat(product.name).isEqualTo("Keyboard")
        assertThat(product.price).isEqualTo(49000L)
        assertThat(product.description).isEqualTo("Mechanical keyboard")
        assertThat(product.status).isEqualTo(ProductStatus.ON_SALE)
    }

    @Test
    fun `product can transition to sold out`() {
        val seller = Seller.create(
            name = "Seller A",
            email = "seller-a@example.com",
        )
        val product = Product.create(
            seller = seller,
            name = "Keyboard",
            price = 49000L,
            description = null,
        )

        product.markSoldOut()

        assertThat(product.status).isEqualTo(ProductStatus.SOLD_OUT)
    }

    @Test
    fun `product create rejects blank name`() {
        val seller = Seller.create(
            name = "Seller A",
            email = "seller-a@example.com",
        )
        assertThatThrownBy {
            Product.create(
                seller = seller,
                name = "   ",
                price = 49000L,
                description = null,
            )
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Product name must not be blank")
    }

    @Test
    fun `product can add options in order`() {
        val seller = Seller.create(
            name = "Seller A",
            email = "seller-a@example.com",
        )
        val product = Product.create(
            seller = seller,
            name = "Keyboard",
            price = 49000L,
            description = null,
        )

        product.addOption(
            name = "Red Switch",
            additionalPrice = 0L,
            stockQuantity = 10,
        )
        product.addOption(
            name = "Blue Switch",
            additionalPrice = 5000L,
            stockQuantity = 5,
        )

        assertThat(product.options).hasSize(2)
        assertThat(product.options.map { it.optionOrder }).containsExactly(0, 1)
        assertThat(product.options.map { it.name }).containsExactly("Red Switch", "Blue Switch")
    }
}
