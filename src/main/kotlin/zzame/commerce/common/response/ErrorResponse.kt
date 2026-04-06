package zzame.commerce.common.response

data class ErrorResponse(
    val code: String,
    val message: String,
    val validationErrors: List<ValidationErrorResponse> = emptyList(),
)
