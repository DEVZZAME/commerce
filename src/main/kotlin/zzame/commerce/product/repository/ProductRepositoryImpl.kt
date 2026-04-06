package zzame.commerce.product.repository

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import zzame.commerce.product.dto.ProductSearchCondition
import zzame.commerce.product.dto.ProductPopularityResponse
import zzame.commerce.product.dto.ProductSummaryResponse
import zzame.commerce.product.entity.Product
import zzame.commerce.order.entity.PaymentStatus
import zzame.commerce.order.entity.QOrder.order
import zzame.commerce.order.entity.QOrderItem.orderItem
import zzame.commerce.product.entity.QProduct.product
import zzame.commerce.product.entity.QProductOption.productOption
import zzame.commerce.product.entity.QSeller.seller
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Repository

@Repository
class ProductRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : ProductRepositoryCustom {
    override fun search(condition: ProductSearchCondition, pageable: Pageable): Page<ProductSummaryResponse> {
        val whereClause = buildWhereClause(condition)

        val content = queryFactory
            .select(
                Projections.constructor(
                    ProductSummaryResponse::class.java,
                    product.id,
                    seller.id,
                    seller.name,
                    product.name,
                    product.price,
                    product.status,
                    product.createdAt,
                ),
            )
            .from(product)
            .join(product.seller, seller)
            .where(whereClause)
            .orderBy(*orderSpecifiers(pageable.sort))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val total = queryFactory
            .select(product.count())
            .from(product)
            .join(product.seller, seller)
            .where(whereClause)
            .fetchOne()
            ?: 0L

        return PageImpl(content, pageable, total)
    }

    override fun findDetailById(productId: Long): Product? = queryFactory
        .selectFrom(product)
        .join(product.seller, seller).fetchJoin()
        .leftJoin(product.optionList, productOption).fetchJoin()
        .where(product.id.eq(productId))
        .distinct()
        .fetchOne()

    override fun findPopularProducts(limit: Int): List<ProductPopularityResponse> {
        val totalSoldQuantity = orderItem.quantity.sum()

        return queryFactory
            .select(
                Projections.constructor(
                    ProductPopularityResponse::class.java,
                    product.id,
                    seller.id,
                    seller.name,
                    product.name,
                    product.price,
                    product.status,
                    totalSoldQuantity,
                ),
            )
            .from(orderItem)
            .join(orderItem.order, order)
            .join(orderItem.product, product)
            .join(product.seller, seller)
            .where(order.paymentStatus.eq(PaymentStatus.PAID))
            .groupBy(
                product.id,
                seller.id,
                seller.name,
                product.name,
                product.price,
                product.status,
                product.createdAt,
            )
            .orderBy(totalSoldQuantity.desc(), product.createdAt.desc())
            .limit(limit.toLong())
            .fetch()
    }

    private fun buildWhereClause(condition: ProductSearchCondition): BooleanBuilder = BooleanBuilder()
        .apply {
            condition.keyword?.let {
                and(
                    product.name.containsIgnoreCase(it)
                        .or(product.description.containsIgnoreCase(it))
                        .or(seller.name.containsIgnoreCase(it)),
                )
            }
            condition.sellerId?.let { and(product.seller.id.eq(it)) }
            condition.status?.let { and(product.status.eq(it)) }
            condition.minPrice?.let { and(product.price.goe(it)) }
            condition.maxPrice?.let { and(product.price.loe(it)) }
        }

    private fun orderSpecifiers(sort: Sort): Array<OrderSpecifier<*>> {
        if (sort.isUnsorted) {
            return arrayOf(product.createdAt.desc(), product.id.desc())
        }

        return sort.toList().map { order ->
            val direction = if (order.isAscending) Order.ASC else Order.DESC

            when (order.property) {
                "price" -> OrderSpecifier(direction, product.price)
                "name" -> OrderSpecifier(direction, product.name)
                "createdAt" -> OrderSpecifier(direction, product.createdAt)
                else -> OrderSpecifier(direction, product.createdAt)
            }
        }.toTypedArray()
    }
}
