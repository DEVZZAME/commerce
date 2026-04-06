package zzame.commerce.order.dto

import zzame.commerce.order.entity.OrderStatus
import zzame.commerce.order.entity.PaymentStatus
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Positive
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import java.time.LocalDate
import java.time.LocalDateTime

data class OrderSearchRequest(
    val customerId: Long? = null,
    val sellerId: Long? = null,
    val orderStatus: OrderStatus? = null,
    val paymentStatus: PaymentStatus? = null,
    val orderedFrom: LocalDate? = null,
    val orderedTo: LocalDate? = null,
    @field:Min(0)
    val page: Int = 0,
    @field:Positive
    @field:Max(100)
    val size: Int = 20,
    val sortBy: String = "orderedAt",
    val direction: Sort.Direction = Sort.Direction.DESC,
) {
    fun toCondition(): OrderSearchCondition = OrderSearchCondition(
        customerId = customerId,
        sellerId = sellerId,
        orderStatus = orderStatus,
        paymentStatus = paymentStatus,
        orderedFrom = orderedFrom?.atStartOfDay(),
        orderedTo = orderedTo?.plusDays(1)?.atStartOfDay(),
    )

    fun toPageable(): Pageable = PageRequest.of(
        page,
        size,
        Sort.by(direction, sortBy),
    )
}
