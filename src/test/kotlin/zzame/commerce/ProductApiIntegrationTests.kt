package zzame.commerce

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import zzame.commerce.product.entity.Seller
import zzame.commerce.product.repository.SellerRepository

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProductApiIntegrationTests {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var sellerRepository: SellerRepository

    @Test
    fun `create product returns created response`() {
        val sellerId = createSeller()

        mockMvc.post("/api/products") {
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                  "sellerId": $sellerId,
                  "name": "Keyboard",
                  "price": 49000,
                  "description": "  Mechanical keyboard  "
                }
            """.trimIndent()
        }.andExpect {
            status { isCreated() }
            jsonPath("$.success") { value(true) }
            jsonPath("$.message") { value("Product created successfully") }
            jsonPath("$.data.id") { value(1) }
            jsonPath("$.data.sellerId") { value(sellerId) }
            jsonPath("$.data.sellerName") { value("Seller One") }
            jsonPath("$.data.name") { value("Keyboard") }
            jsonPath("$.data.price") { value(49000) }
            jsonPath("$.data.description") { value("Mechanical keyboard") }
            jsonPath("$.data.status") { value("ON_SALE") }
        }
    }

    @Test
    fun `get products returns created product list`() {
        val sellerId = createSeller()
        createProduct(sellerId, "Keyboard", 49000)

        mockMvc.get("/api/products")
            .andExpect {
                status { isOk() }
                jsonPath("$.success") { value(true) }
                jsonPath("$.message") { value("Products fetched successfully") }
                jsonPath("$.data.content[0].id") { value(1) }
                jsonPath("$.data.content[0].name") { value("Keyboard") }
                jsonPath("$.data.content[0].sellerId") { value(sellerId) }
                jsonPath("$.data.totalElements") { value(1) }
            }
    }

    @Test
    fun `get product returns single product`() {
        val sellerId = createSeller()
        createProduct(sellerId, "Keyboard", 49000)

        mockMvc.get("/api/products/1")
            .andExpect {
                status { isOk() }
                jsonPath("$.success") { value(true) }
                jsonPath("$.message") { value("Product fetched successfully") }
                jsonPath("$.data.id") { value(1) }
                jsonPath("$.data.sellerId") { value(sellerId) }
                jsonPath("$.data.sellerName") { value("Seller One") }
                jsonPath("$.data.name") { value("Keyboard") }
            }
    }

    @Test
    fun `get product returns not found when product does not exist`() {
        mockMvc.get("/api/products/999")
            .andExpect {
                status { isNotFound() }
                jsonPath("$.success") { value(false) }
                jsonPath("$.message") { value("Product not found. id=999") }
                jsonPath("$.data.code") { value("PRODUCT-404") }
            }
    }

    @Test
    fun `create product returns validation error for invalid request`() {
        mockMvc.post("/api/products") {
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                  "sellerId": 0,
                  "name": " ",
                  "price": 0,
                  "description": "valid"
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

    @Test
    fun `get products applies paging and sorting`() {
        val sellerId = createSeller()
        createProduct(sellerId, "Keyboard", 49000)
        createProduct(sellerId, "Monitor", 200000)
        createProduct(sellerId, "Mouse", 30000)

        mockMvc.get("/api/products") {
            param("page", "0")
            param("size", "2")
            param("sortBy", "price")
            param("direction", "DESC")
        }.andExpect {
            status { isOk() }
            jsonPath("$.data.content.length()") { value(2) }
            jsonPath("$.data.content[0].name") { value("Monitor") }
            jsonPath("$.data.content[1].name") { value("Keyboard") }
            jsonPath("$.data.totalElements") { value(3) }
            jsonPath("$.data.hasNext") { value(true) }
        }
    }

    @Test
    fun `get products applies search filters`() {
        val sellerId = createSeller()
        createProduct(sellerId, "Gaming Keyboard", 150000)
        createProduct(sellerId, "Office Keyboard", 70000)
        createProduct(sellerId, "Mouse", 30000)

        mockMvc.get("/api/products") {
            param("keyword", "keyboard")
            param("minPrice", "100000")
        }.andExpect {
            status { isOk() }
            jsonPath("$.data.content.length()") { value(1) }
            jsonPath("$.data.content[0].name") { value("Gaming Keyboard") }
        }
    }

    private fun createSeller(): Long = sellerRepository.save(
        Seller.create(
            name = "Seller One",
            email = "seller-one@example.com",
        ),
    ).id

    private fun createProduct(
        sellerId: Long,
        name: String,
        price: Long,
    ) {
        mockMvc.post("/api/products") {
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                  "sellerId": $sellerId,
                  "name": "$name",
                  "price": $price,
                  "description": "Mechanical keyboard"
                }
            """.trimIndent()
        }.andExpect {
            status { isCreated() }
        }
    }
}
