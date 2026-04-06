package zzame.commerce.cart.controller

import zzame.commerce.cart.dto.CartAddItemRequest
import zzame.commerce.cart.dto.CartResponse
import zzame.commerce.cart.service.CartService
import zzame.commerce.common.response.RestResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/carts/{customerId}")
class CartController(
    private val cartService: CartService,
) {
    @GetMapping
    fun getCart(
        @PathVariable customerId: Long,
    ): RestResponse<CartResponse> = RestResponse.success(
        data = cartService.getCart(customerId),
        message = "Cart fetched successfully",
    )

    @PostMapping("/items")
    fun addItem(
        @PathVariable customerId: Long,
        @Valid @RequestBody request: CartAddItemRequest,
    ): ResponseEntity<RestResponse<CartResponse>> = ResponseEntity.status(HttpStatus.CREATED)
        .body(
            RestResponse.success(
                data = cartService.addItem(customerId, request),
                message = "Cart item added successfully",
            ),
        )

    @DeleteMapping("/items/{cartItemId}")
    fun removeItem(
        @PathVariable customerId: Long,
        @PathVariable cartItemId: Long,
    ): RestResponse<CartResponse> = RestResponse.success(
        data = cartService.removeItem(customerId, cartItemId),
        message = "Cart item removed successfully",
    )
}
