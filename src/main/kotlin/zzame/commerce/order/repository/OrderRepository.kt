package zzame.commerce.order.repository

import zzame.commerce.order.entity.Order
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface OrderRepository : JpaRepository<Order, Long>, OrderRepositoryCustom {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(
        """
        select distinct o
        from Order o
        left join fetch o.itemList oi
        left join fetch oi.product p
        left join fetch p.seller
        left join fetch oi.productOption
        where o.id = :orderId
        """,
    )
    fun findDetailByIdForUpdate(@Param("orderId") orderId: Long): Order?
}
