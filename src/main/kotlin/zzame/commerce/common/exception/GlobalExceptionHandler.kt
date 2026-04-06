package zzame.commerce.common.exception

import zzame.commerce.common.response.ErrorResponse
import zzame.commerce.common.response.RestResponse
import zzame.commerce.common.response.ValidationErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(CommerceException::class)
    fun handleCommerceException(exception: CommerceException): ResponseEntity<RestResponse<ErrorResponse>> {
        val errorResponse = ErrorResponse(
            code = exception.errorCode.code,
            message = exception.message,
        )

        return ResponseEntity.status(exception.errorCode.status)
            .body(RestResponse.failure(message = exception.message, data = errorResponse))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(
        exception: MethodArgumentNotValidException,
    ): ResponseEntity<RestResponse<ErrorResponse>> {
        val validationErrors = exception.bindingResult.fieldErrors.map { it.toValidationErrorResponse() }
        val errorCode = ErrorCode.INVALID_INPUT
        val errorResponse = ErrorResponse(
            code = errorCode.code,
            message = errorCode.message,
            validationErrors = validationErrors,
        )

        return ResponseEntity.status(errorCode.status)
            .body(RestResponse.failure(message = errorCode.message, data = errorResponse))
    }

    @ExceptionHandler(Exception::class)
    fun handleUnexpectedException(exception: Exception): ResponseEntity<RestResponse<ErrorResponse>> {
        val errorCode = ErrorCode.INTERNAL_SERVER_ERROR
        val errorResponse = ErrorResponse(
            code = errorCode.code,
            message = errorCode.message,
        )

        return ResponseEntity.status(errorCode.status)
            .body(RestResponse.failure(message = errorCode.message, data = errorResponse))
    }

    private fun FieldError.toValidationErrorResponse(): ValidationErrorResponse = ValidationErrorResponse(
        field = field,
        message = defaultMessage ?: "Invalid value",
    )
}
