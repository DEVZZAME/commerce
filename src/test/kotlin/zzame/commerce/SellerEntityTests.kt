package zzame.commerce

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import zzame.commerce.product.entity.Seller
import zzame.commerce.product.entity.SellerStatus

class SellerEntityTests {

    @Test
    fun `seller create normalizes email and defaults to active`() {
        val seller = Seller.create(
            name = " Seller One ",
            email = "SELLER@EXAMPLE.COM ",
        )

        assertThat(seller.name).isEqualTo("Seller One")
        assertThat(seller.email).isEqualTo("seller@example.com")
        assertThat(seller.status).isEqualTo(SellerStatus.ACTIVE)
    }

    @Test
    fun `seller can be deactivated`() {
        val seller = Seller.create(
            name = "Seller One",
            email = "seller@example.com",
        )

        seller.deactivate()

        assertThat(seller.status).isEqualTo(SellerStatus.INACTIVE)
    }
}
