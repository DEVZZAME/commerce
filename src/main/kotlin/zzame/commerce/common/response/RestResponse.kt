package zzame.commerce.common.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class RestResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T? = null,
) {
    companion object {
        private const val DEFAULT_SUCCESS_MESSAGE = "Request succeeded"
        private const val DEFAULT_FAILURE_MESSAGE = "Request failed"

        fun <T> success(
            data: T,
            message: String = DEFAULT_SUCCESS_MESSAGE,
        ): RestResponse<T> = RestResponse(
            success = true,
            message = message,
            data = data,
        )

        fun success(
            message: String = DEFAULT_SUCCESS_MESSAGE,
        ): RestResponse<Unit> = RestResponse(
            success = true,
            message = message,
        )

        fun <T> failure(
            message: String = DEFAULT_FAILURE_MESSAGE,
            data: T? = null,
        ): RestResponse<T> = RestResponse(
            success = false,
            message = message,
            data = data,
        )
    }
}
