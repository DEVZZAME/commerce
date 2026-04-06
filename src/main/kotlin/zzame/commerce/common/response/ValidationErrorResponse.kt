package zzame.commerce.common.response

data class ValidationErrorResponse(
    val field: String,
    val message: String,
)
