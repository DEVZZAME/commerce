package zzame.commerce.product.repository

import zzame.commerce.product.entity.Product
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@Repository
class InMemoryProductRepository : ProductRepository {
    private val sequence = AtomicLong(0)
    private val storage = ConcurrentHashMap<Long, Product>()

    override fun findAll(): List<Product> = storage.values.sortedBy(Product::id)

    override fun findById(id: Long): Product? = storage[id]

    override fun save(product: Product): Product {
        val persistedProduct = if (product.id == 0L) {
            product.copy(id = sequence.incrementAndGet())
        } else {
            product
        }

        storage[persistedProduct.id] = persistedProduct

        return persistedProduct
    }
}
