package zzame.commerce.order.service

import zzame.commerce.cart.repository.CartRepository
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
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val cartRepository: CartRepository,
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
    fun completePayment(orderId: Long): OrderResponse {
        val order = orderRepository.findDetailById(orderId)
            ?: throw OrderNotFoundException(orderId)

        if (order.status == OrderStatus.PAID) {
            return OrderResponse.from(order)
        }

        order.items.forEach { item ->
            val option = item.productOption

            if (option.stockQuantity < item.quantity) {
                throw InsufficientStockException(
                    productOptionId = option.id,
                    currentStock = option.stockQuantity,
                    requestedQuantity = item.quantity,
                )
            }

            option.deductStock(item.quantity)

            if (item.product.options.sumOf { productOption -> productOption.stockQuantity } == 0) {
                item.product.markSoldOut()
            }
        }

        order.markPaid()

        return OrderResponse.from(order)
    }

    @Transactional(readOnly = true)
    fun getOrders(searchRequest: OrderSearchRequest): PageResponse<OrderSummaryResponse> = orderRepository.search(
        condition = searchRequest.toCondition(),
        pageable = searchRequest.toPageable(),
    ).let { page -> PageResponse.from(page) }
}
