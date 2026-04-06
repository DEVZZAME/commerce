package zzame.commerce.product.repository

import zzame.commerce.product.entity.ProductOption
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ProductOptionRepository : JpaRepository<ProductOption, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(
        """
        select po
        from ProductOption po
        join fetch po.product p
        where po.id in :productOptionIds
        """,
    )
    fun findAllForUpdateByIdIn(
        @Param("productOptionIds") productOptionIds: Collection<Long>,
    ): List<ProductOption>

    fun existsByProductIdAndStockQuantityGreaterThan(productId: Long, stockQuantity: Int): Boolean
}
