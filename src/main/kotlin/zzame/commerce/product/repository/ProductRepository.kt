package zzame.commerce.product.repository

import zzame.commerce.product.entity.Product

interface ProductRepository {
    fun findAll(): List<Product>
    fun findById(id: Long): Product?
    fun save(product: Product): Product
}
