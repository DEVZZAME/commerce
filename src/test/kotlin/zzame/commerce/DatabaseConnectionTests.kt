package zzame.commerce

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource

@SpringBootTest
@ActiveProfiles("local")
@TestPropertySource(properties = ["spring.autoconfigure.exclude="])
class DatabaseConnectionTests {

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    @Test
    fun `database connection succeeds`() {
        val result = jdbcTemplate.queryForObject("SELECT 1", Int::class.java)
        assertThat(result).isEqualTo(1)
    }
}
