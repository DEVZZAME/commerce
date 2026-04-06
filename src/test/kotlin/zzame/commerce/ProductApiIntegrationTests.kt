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

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProductApiIntegrationTests {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `create product returns created response`() {
        mockMvc.post("/api/products") {
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                  "sellerId": 1,
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
            jsonPath("$.data.sellerId") { value(1) }
            jsonPath("$.data.name") { value("Keyboard") }
            jsonPath("$.data.price") { value(49000) }
            jsonPath("$.data.description") { value("Mechanical keyboard") }
        }
    }

    @Test
    fun `get products returns created product list`() {
        createProduct()

        mockMvc.get("/api/products")
            .andExpect {
                status { isOk() }
                jsonPath("$.success") { value(true) }
                jsonPath("$.message") { value("Products fetched successfully") }
                jsonPath("$.data[0].id") { value(1) }
                jsonPath("$.data[0].name") { value("Keyboard") }
            }
    }

    @Test
    fun `get product returns single product`() {
        createProduct()

        mockMvc.get("/api/products/1")
            .andExpect {
                status { isOk() }
                jsonPath("$.success") { value(true) }
                jsonPath("$.message") { value("Product fetched successfully") }
                jsonPath("$.data.id") { value(1) }
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

    private fun createProduct() {
        mockMvc.post("/api/products") {
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                  "sellerId": 1,
                  "name": "Keyboard",
                  "price": 49000,
                  "description": "Mechanical keyboard"
                }
            """.trimIndent()
        }.andExpect {
            status { isCreated() }
        }
    }
}
