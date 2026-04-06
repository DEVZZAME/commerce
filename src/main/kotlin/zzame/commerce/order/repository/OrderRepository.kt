package zzame.commerce.order.repository

import zzame.commerce.order.entity.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, Long>, OrderRepositoryCustom
