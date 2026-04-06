package zzame.commerce

import jakarta.transaction.Transactional
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import zzame.commerce.cart.repository.CartRepository
import zzame.commerce.product.entity.Product
import zzame.commerce.product.entity.Seller
import zzame.commerce.product.repository.ProductRepository
import zzame.commerce.product.repository.SellerRepository

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class OrderApiIntegrationTests {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var sellerRepository: SellerRepository

    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var cartRepository: CartRepository

    @Test
    fun `create order consumes cart and returns created order`() {
        val customerId = 100L
        val (productId, optionId) = createProductWithOption()
        addCartItem(customerId, productId, optionId, 2)

        mockMvc.post("/api/orders") {
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                  "customerId": $customerId,
                  "recipientName": "홍길동",
                  "phone": "010-1111-2222",
                  "address": "서울시 강남구 테헤란로",
                  "detailAddress": "101동 202호",
                  "deliveryRequest": "문 앞 배송"
                }
            """.trimIndent()
        }.andExpect {
            status { isCreated() }
            jsonPath("$.success") { value(true) }
            jsonPath("$.message") { value("Order created successfully") }
            jsonPath("$.data.customerId") { value(customerId) }
            jsonPath("$.data.items.length()") { value(1) }
            jsonPath("$.data.totalQuantity") { value(2) }
            jsonPath("$.data.totalPrice") { value(104000) }
            jsonPath("$.data.paymentStatus") { value("READY") }
        }

        mockMvc.get("/api/carts/$customerId")
            .andExpect {
                status { isOk() }
                jsonPath("$.data.items.length()") { value(0) }
            }
    }

    @Test
    fun `complete payment deducts stock and marks product sold out when stock reaches zero`() {
        val customerId = 101L
        val (productId, optionId) = createProductWithOption(stockQuantity = 2)
        addCartItem(customerId, productId, optionId, 2)
        createOrder(customerId)

        mockMvc.post("/api/orders/1/payments/complete")
            .andExpect {
                status { isOk() }
                jsonPath("$.success") { value(true) }
                jsonPath("$.message") { value("Payment completed successfully") }
                jsonPath("$.data.status") { value("PAID") }
                jsonPath("$.data.paymentStatus") { value("PAID") }
            }

        mockMvc.get("/api/products/$productId")
            .andExpect {
                status { isOk() }
                jsonPath("$.data.status") { value("SOLD_OUT") }
                jsonPath("$.data.options[0].stockQuantity") { value(0) }
            }
    }

    @Test
    fun `complete payment returns conflict when stock is insufficient`() {
        val customerId = 102L
        val (productId, optionId) = createProductWithOption(stockQuantity = 1)
        addCartItem(customerId, productId, optionId, 2)
        createOrder(customerId)

        mockMvc.post("/api/orders/1/payments/complete")
            .andExpect {
                status { isConflict() }
                jsonPath("$.success") { value(false) }
                jsonPath("$.data.code") { value("PRODUCT_OPTION-409") }
            }
    }

    @Test
    fun `get orders applies customer and payment status filters`() {
        val (productId, optionId) = createProductWithOption()
        addCartItem(201L, productId, optionId, 1)
        addCartItem(202L, productId, optionId, 1)
        createOrder(201L)
        createOrder(202L)
        mockMvc.post("/api/orders/1/payments/complete").andExpect { status { isOk() } }

        mockMvc.get("/api/orders") {
            param("customerId", "201")
            param("paymentStatus", "PAID")
        }.andExpect {
            status { isOk() }
            jsonPath("$.success") { value(true) }
            jsonPath("$.message") { value("Orders fetched successfully") }
            jsonPath("$.data.content.length()") { value(1) }
            jsonPath("$.data.content[0].customerId") { value(201) }
            jsonPath("$.data.content[0].paymentStatus") { value("PAID") }
        }
    }

    private fun createProductWithOption(stockQuantity: Int = 10): Pair<Long, Long> {
        val seller = sellerRepository.save(
            Seller.create(
                name = "셀러 원",
                email = "seller-one@example.com",
            ),
        )
        val product = Product.create(
            seller = seller,
            name = "키보드",
            price = 49000L,
            description = "기계식 키보드",
        ).apply {
            addOption(
                name = "기본축",
                additionalPrice = 3000L,
                stockQuantity = stockQuantity,
                description = "기본 옵션",
            )
        }
        val saved = productRepository.save(product)
        return saved.id to saved.options.first().id
    }

    private fun addCartItem(customerId: Long, productId: Long, optionId: Long, quantity: Int) {
        mockMvc.post("/api/carts/$customerId/items") {
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                  "productId": $productId,
                  "productOptionId": $optionId,
                  "quantity": $quantity
                }
            """.trimIndent()
        }.andExpect {
            status { isCreated() }
        }
    }

    private fun createOrder(customerId: Long) {
        mockMvc.post("/api/orders") {
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                  "customerId": $customerId,
                  "recipientName": "주문자",
                  "phone": "010-1234-5678",
                  "address": "서울시 송파구",
                  "detailAddress": "3층",
                  "deliveryRequest": null
                }
            """.trimIndent()
        }.andExpect {
            status { isCreated() }
        }
    }
}
