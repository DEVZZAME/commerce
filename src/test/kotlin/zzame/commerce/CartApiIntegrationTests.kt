package zzame.commerce

import jakarta.transaction.Transactional
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import zzame.commerce.product.entity.Product
import zzame.commerce.product.entity.Seller
import zzame.commerce.product.repository.ProductRepository
import zzame.commerce.product.repository.SellerRepository

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CartApiIntegrationTests {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var sellerRepository: SellerRepository

    @Autowired
    lateinit var productRepository: ProductRepository

    @Test
    fun `get cart returns empty cart when cart does not exist`() {
        mockMvc.get("/api/carts/100")
            .andExpect {
                status { isOk() }
                jsonPath("$.success") { value(true) }
                jsonPath("$.message") { value("Cart fetched successfully") }
                jsonPath("$.data.customerId") { value(100) }
                jsonPath("$.data.items.length()") { value(0) }
                jsonPath("$.data.totalQuantity") { value(0) }
                jsonPath("$.data.totalPrice") { value(0) }
            }
    }

    @Test
    fun `add item creates cart and returns cart response`() {
        val (productId, optionId) = createProductWithOption()

        mockMvc.post("/api/carts/100/items") {
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                  "productId": $productId,
                  "productOptionId": $optionId,
                  "quantity": 2
                }
            """.trimIndent()
        }.andExpect {
            status { isCreated() }
            jsonPath("$.success") { value(true) }
            jsonPath("$.message") { value("Cart item added successfully") }
            jsonPath("$.data.customerId") { value(100) }
            jsonPath("$.data.items.length()") { value(1) }
            jsonPath("$.data.items[0].productId") { value(productId) }
            jsonPath("$.data.items[0].productOptionId") { value(optionId) }
            jsonPath("$.data.items[0].productName") { value("키보드") }
            jsonPath("$.data.items[0].optionName") { value("기본축") }
            jsonPath("$.data.items[0].sellerName") { value("셀러 원") }
            jsonPath("$.data.items[0].quantity") { value(2) }
            jsonPath("$.data.items[0].unitPrice") { value(52000) }
            jsonPath("$.data.items[0].linePrice") { value(104000) }
            jsonPath("$.data.totalQuantity") { value(2) }
            jsonPath("$.data.totalPrice") { value(104000) }
        }
    }

    @Test
    fun `add same item twice merges quantity`() {
        val (productId, optionId) = createProductWithOption()

        repeat(2) {
            mockMvc.post("/api/carts/100/items") {
                contentType = MediaType.APPLICATION_JSON
                content = """
                    {
                      "productId": $productId,
                      "productOptionId": $optionId,
                      "quantity": 1
                    }
                """.trimIndent()
            }.andExpect {
                status { isCreated() }
            }
        }

        mockMvc.get("/api/carts/100")
            .andExpect {
                status { isOk() }
                jsonPath("$.data.items.length()") { value(1) }
                jsonPath("$.data.items[0].quantity") { value(2) }
                jsonPath("$.data.totalQuantity") { value(2) }
                jsonPath("$.data.totalPrice") { value(104000) }
            }
    }

    @Test
    fun `remove item deletes item from cart`() {
        val (productId, optionId) = createProductWithOption()

        mockMvc.post("/api/carts/100/items") {
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                  "productId": $productId,
                  "productOptionId": $optionId,
                  "quantity": 1
                }
            """.trimIndent()
        }.andExpect {
            status { isCreated() }
        }

        mockMvc.delete("/api/carts/100/items/1")
            .andExpect {
                status { isOk() }
                jsonPath("$.success") { value(true) }
                jsonPath("$.message") { value("Cart item removed successfully") }
                jsonPath("$.data.items.length()") { value(0) }
                jsonPath("$.data.totalQuantity") { value(0) }
                jsonPath("$.data.totalPrice") { value(0) }
            }
    }

    @Test
    fun `add item returns validation error for invalid request`() {
        mockMvc.post("/api/carts/100/items") {
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                  "productId": 0,
                  "productOptionId": 0,
                  "quantity": 0
                }
            """.trimIndent()
        }.andExpect {
            status { isBadRequest() }
            jsonPath("$.success") { value(false) }
            jsonPath("$.message") { value("Invalid request input") }
            jsonPath("$.data.code") { value("COMMON-400") }
            jsonPath("$.data.validationErrors.length()") { value(3) }
        }
    }

    private fun createProductWithOption(): Pair<Long, Long> {
        val seller = sellerRepository.save(
            Seller.create(
                name = "셀러 원",
                email = "seller-one@example.com",
            ),
        )
        val product = Product.create(
            seller = seller,
            name = "키보드",
            price = 49000,
            description = "기계식 키보드",
        ).apply {
            addOption(
                name = "기본축",
                additionalPrice = 3000,
                stockQuantity = 10,
                description = "기본 옵션",
            )
        }

        val savedProduct = productRepository.save(product)
        return savedProduct.id to savedProduct.options.first().id
    }
}
