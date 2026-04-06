package zzame.commerce.order.controller

import zzame.commerce.common.response.PageResponse
import zzame.commerce.common.response.RestResponse
import zzame.commerce.order.dto.OrderCreateRequest
import zzame.commerce.order.dto.OrderResponse
import zzame.commerce.order.dto.OrderSearchRequest
import zzame.commerce.order.dto.OrderSummaryResponse
import zzame.commerce.order.service.OrderService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/orders")
class OrderController(
    private val orderService: OrderService,
) {
    @PostMapping
    fun createOrder(
        @Valid @RequestBody request: OrderCreateRequest,
    ): ResponseEntity<RestResponse<OrderResponse>> = ResponseEntity.status(HttpStatus.CREATED)
        .body(
            RestResponse.success(
                data = orderService.createOrder(request),
                message = "Order created successfully",
            ),
        )

    @PostMapping("/{orderId}/payments/complete")
    fun completePayment(
        @PathVariable orderId: Long,
    ): RestResponse<OrderResponse> = RestResponse.success(
        data = orderService.completePayment(orderId),
        message = "Payment completed successfully",
    )

    @GetMapping
    fun getOrders(
        @Valid @ModelAttribute request: OrderSearchRequest,
    ): RestResponse<PageResponse<OrderSummaryResponse>> = RestResponse.success(
        data = orderService.getOrders(request),
        message = "Orders fetched successfully",
    )
}
