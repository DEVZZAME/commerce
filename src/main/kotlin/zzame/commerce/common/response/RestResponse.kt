package zzame.commerce.common.response

data class RestResponse<T>(
    val success: Boolean,
    val data: T,
) {
    companion object {
        fun <T> success(data: T): RestResponse<T> = RestResponse(
            success = true,
            data = data,
        )
    }
}
