package zzame.commerce.order.repository

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Order as QueryOrder
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import zzame.commerce.order.dto.OrderSearchCondition
import zzame.commerce.order.dto.OrderSummaryResponse
import zzame.commerce.order.entity.Order
import zzame.commerce.order.entity.QOrder.order
import zzame.commerce.order.entity.QOrderItem.orderItem
import zzame.commerce.product.entity.QProduct.product
import zzame.commerce.product.entity.QSeller.seller
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Repository

@Repository
class OrderRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : OrderRepositoryCustom {
    override fun search(condition: OrderSearchCondition, pageable: Pageable): Page<OrderSummaryResponse> {
        val whereClause = buildWhereClause(condition)

        val content = queryFactory
            .select(
                Projections.constructor(
                    OrderSummaryResponse::class.java,
                    order.id,
                    order.orderNumber,
                    order.customerId,
                    order.recipientName,
                    order.status,
                    order.paymentStatus,
                    order.totalQuantity,
                    order.totalPrice,
                    order.orderedAt,
                ),
            )
            .from(order)
            .leftJoin(order.itemList, orderItem)
            .leftJoin(orderItem.product, product)
            .leftJoin(product.seller, seller)
            .where(whereClause)
            .distinct()
            .orderBy(*orderSpecifiers(pageable.sort))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val total = queryFactory
            .select(order.countDistinct())
            .from(order)
            .leftJoin(order.itemList, orderItem)
            .leftJoin(orderItem.product, product)
            .leftJoin(product.seller, seller)
            .where(whereClause)
            .fetchOne()
            ?: 0L

        return PageImpl(content, pageable, total)
    }

    override fun findDetailById(orderId: Long): Order? = queryFactory
        .selectFrom(order)
        .leftJoin(order.itemList, orderItem).fetchJoin()
        .leftJoin(orderItem.product, product).fetchJoin()
        .leftJoin(product.seller, seller).fetchJoin()
        .where(order.id.eq(orderId))
        .distinct()
        .fetchOne()

    private fun buildWhereClause(condition: OrderSearchCondition): BooleanBuilder = BooleanBuilder()
        .apply {
            condition.customerId?.let { and(order.customerId.eq(it)) }
            condition.sellerId?.let { and(product.seller.id.eq(it)) }
            condition.orderStatus?.let { and(order.status.eq(it)) }
            condition.paymentStatus?.let { and(order.paymentStatus.eq(it)) }
            condition.orderedFrom?.let { and(order.orderedAt.goe(it)) }
            condition.orderedTo?.let { and(order.orderedAt.lt(it)) }
        }

    private fun orderSpecifiers(sort: Sort): Array<OrderSpecifier<*>> {
        if (sort.isUnsorted) {
            return arrayOf(order.orderedAt.desc(), order.id.desc())
        }

        return sort.toList().map { sortOrder ->
            val direction = if (sortOrder.isAscending) QueryOrder.ASC else QueryOrder.DESC

            when (sortOrder.property) {
                "totalPrice" -> OrderSpecifier(direction, order.totalPrice)
                "orderedAt" -> OrderSpecifier(direction, order.orderedAt)
                "orderNumber" -> OrderSpecifier(direction, order.orderNumber)
                else -> OrderSpecifier(direction, order.orderedAt)
            }
        }.toTypedArray()
    }
}
