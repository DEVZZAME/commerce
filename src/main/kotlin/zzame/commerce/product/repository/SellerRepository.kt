package zzame.commerce.product.repository

import zzame.commerce.product.entity.Seller
import org.springframework.data.jpa.repository.JpaRepository

interface SellerRepository : JpaRepository<Seller, Long>
