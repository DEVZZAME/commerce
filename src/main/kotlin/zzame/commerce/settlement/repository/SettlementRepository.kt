package zzame.commerce.settlement.repository

import zzame.commerce.settlement.entity.Settlement
import org.springframework.data.jpa.repository.JpaRepository

interface SettlementRepository : JpaRepository<Settlement, Long> {
    fun findBySellerIdAndStartDateAndEndDate(
        sellerId: Long,
        startDate: java.time.LocalDate,
        endDate: java.time.LocalDate,
    ): Settlement?
}
