package zzame.commerce.order.service

import zzame.commerce.cart.repository.CartRepository
import zzame.commerce.cart.exception.ProductOptionNotFoundException
import zzame.commerce.common.response.PageResponse
import zzame.commerce.order.dto.OrderCreateRequest
import zzame.commerce.order.dto.OrderResponse
import zzame.commerce.order.dto.OrderSearchRequest
import zzame.commerce.order.dto.OrderSummaryResponse
import zzame.commerce.order.entity.Order
import zzame.commerce.order.entity.OrderStatus
import zzame.commerce.order.exception.EmptyCartException
import zzame.commerce.order.exception.InsufficientStockException
import zzame.commerce.order.exception.OrderNotFoundException
import zzame.commerce.order.repository.OrderRepository
import zzame.commerce.product.repository.ProductOptionRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val cartRepository: CartRepository,
    private val productOptionRepository: ProductOptionRepository,
) {
    @Transactional
    fun createOrder(request: OrderCreateRequest): OrderResponse {
        val cart = cartRepository.findByCustomerId(request.customerId)
            ?: throw EmptyCartException(request.customerId)

        if (cart.items.isEmpty()) {
            throw EmptyCartException(request.customerId)
        }

        val order = Order.create(
            customerId = request.customerId,
            recipientName = request.recipientName,
            phone = request.phone,
            address = request.address,
            detailAddress = request.detailAddress,
            deliveryRequest = request.normalizedDeliveryRequest,
            cartItems = cart.items,
        )

        val savedOrder = orderRepository.save(order)
        cart.clearItems()

        return orderRepository.findDetailById(savedOrder.id)
            ?.let(OrderResponse::from)
            ?: OrderResponse.from(savedOrder)
    }

    @Transactional
    @CacheEvict(cacheNames = ["popular-products"], allEntries = true)
    fun completePayment(orderId: Long): OrderResponse {
        val order = orderRepository.findDetailByIdForUpdate(orderId)
            ?: throw OrderNotFoundException(orderId)

        if (order.status == OrderStatus.PAID) {
            return OrderResponse.from(order)
        }

        val lockedOptions = productOptionRepository.findAllForUpdateByIdIn(
            order.items.map { it.productOption.id }.distinct(),
        ).associateBy { it.id }

        order.items.forEach { item ->
            val option = lockedOptions[item.productOption.id]
                ?: throw ProductOptionNotFoundException(item.productOption.id)

            if (option.stockQuantity < item.quantity) {
                throw InsufficientStockException(
                    productOptionId = option.id,
                    currentStock = option.stockQuantity,
                    requestedQuantity = item.quantity,
                )
            }

        }

        order.items.forEach { item ->
            val option = lockedOptions.getValue(item.productOption.id)
            option.deductStock(item.quantity)
        }

        order.items.map { it.product }.distinctBy { it.id }.forEach { product ->
            val hasRemainingStock = productOptionRepository.existsByProductIdAndStockQuantityGreaterThan(product.id, 0)

            if (!hasRemainingStock) {
                product.markSoldOut()
            }
        }

        order.markPaid()

        return orderRepository.findDetailById(order.id)
            ?.let(OrderResponse::from)
            ?: OrderResponse.from(order)
    }

    @Transactional(readOnly = true)
    fun getOrders(searchRequest: OrderSearchRequest): PageResponse<OrderSummaryResponse> = orderRepository.search(
        condition = searchRequest.toCondition(),
        pageable = searchRequest.toPageable(),
    ).let { page -> PageResponse.from(page) }
}
