package zzame.commerce

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import zzame.commerce.common.util.trimToNull

class StringExtensionsTests {

    @Test
    fun `trimToNull returns null for blank string`() {
        assertThat("   ".trimToNull()).isNull()
    }

    @Test
    fun `trimToNull trims non blank string`() {
        assertThat("  commerce  ".trimToNull()).isEqualTo("commerce")
    }

    @Test
    fun `trimToNull returns null for null input`() {
        val value: String? = null

        assertThat(value.trimToNull()).isNull()
    }
}
