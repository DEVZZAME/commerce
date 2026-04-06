package zzame.commerce.order.repository

import zzame.commerce.order.dto.OrderSearchCondition
import zzame.commerce.order.dto.OrderSummaryResponse
import zzame.commerce.order.entity.Order
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface OrderRepositoryCustom {
    fun search(condition: OrderSearchCondition, pageable: Pageable): Page<OrderSummaryResponse>
    fun findDetailById(orderId: Long): Order?
}
