package zzame.commerce.cart.repository

import zzame.commerce.cart.entity.Cart
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface CartRepository : JpaRepository<Cart, Long> {
    @EntityGraph(attributePaths = ["itemList", "itemList.product", "itemList.product.seller", "itemList.productOption"])
    fun findByCustomerId(customerId: Long): Cart?
}
