package zzame.commerce.common.exception

open class CommerceException(
    val errorCode: ErrorCode,
    override val message: String = errorCode.message,
) : RuntimeException(message)
