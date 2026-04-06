package zzame.commerce.product.repository

import zzame.commerce.product.entity.ProductOption
import org.springframework.data.jpa.repository.JpaRepository

interface ProductOptionRepository : JpaRepository<ProductOption, Long>
