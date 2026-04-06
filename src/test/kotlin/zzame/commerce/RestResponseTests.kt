package zzame.commerce

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import zzame.commerce.common.response.RestResponse

class RestResponseTests {

    @Test
    fun `success response includes payload`() {
        val response = RestResponse.success(data = "ok")

        assertThat(response.success).isTrue()
        assertThat(response.message).isEqualTo("Request succeeded")
        assertThat(response.data).isEqualTo("ok")
    }

    @Test
    fun `success response can be created without payload`() {
        val response = RestResponse.success()

        assertThat(response.success).isTrue()
        assertThat(response.message).isEqualTo("Request succeeded")
        assertThat(response.data).isNull()
    }

    @Test
    fun `failure response omits payload`() {
        val response = RestResponse.failure<Unit>(message = "Validation failed")

        assertThat(response.success).isFalse()
        assertThat(response.message).isEqualTo("Validation failed")
        assertThat(response.data).isNull()
    }
}
