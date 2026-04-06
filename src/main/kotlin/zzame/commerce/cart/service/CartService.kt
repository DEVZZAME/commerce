package zzame.commerce.cart.service

import zzame.commerce.cart.dto.CartAddItemRequest
import zzame.commerce.cart.dto.CartResponse
import zzame.commerce.cart.entity.Cart
import zzame.commerce.cart.exception.CartItemNotFoundException
import zzame.commerce.cart.exception.ProductOptionNotFoundException
import zzame.commerce.cart.repository.CartRepository
import zzame.commerce.common.exception.CommerceException
import zzame.commerce.common.exception.ErrorCode
import zzame.commerce.product.exception.ProductNotFoundException
import zzame.commerce.product.repository.ProductOptionRepository
import zzame.commerce.product.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CartService(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
    private val productOptionRepository: ProductOptionRepository,
) {
    @Transactional(readOnly = true)
    fun getCart(customerId: Long): CartResponse = cartRepository.findByCustomerId(customerId)
        ?.let(CartResponse::from)
        ?: CartResponse.empty(customerId)

    @Transactional
    fun addItem(customerId: Long, request: CartAddItemRequest): CartResponse {
        val product = productRepository.findById(request.productId)
            .orElseThrow { ProductNotFoundException(request.productId) }
        val productOption = productOptionRepository.findById(request.productOptionId)
            .orElseThrow { ProductOptionNotFoundException(request.productOptionId) }

        if (productOption.product.id != product.id) {
            throw CommerceException(
                errorCode = ErrorCode.INVALID_INPUT,
                message = "Product option does not belong to product. productId=${product.id}, productOptionId=${productOption.id}",
            )
        }

        val cart = cartRepository.findByCustomerId(customerId)
            ?: cartRepository.save(Cart.create(customerId))

        cart.addItem(
            product = product,
            productOption = productOption,
            quantity = request.quantity,
        )

        return CartResponse.from(cart)
    }

    @Transactional
    fun removeItem(
        customerId: Long,
        cartItemId: Long,
    ): CartResponse {
        val cart = cartRepository.findByCustomerId(customerId)
            ?: throw CartItemNotFoundException(cartItemId)

        if (!cart.removeItem(cartItemId)) {
            throw CartItemNotFoundException(cartItemId)
        }

        return CartResponse.from(cart)
    }
}
