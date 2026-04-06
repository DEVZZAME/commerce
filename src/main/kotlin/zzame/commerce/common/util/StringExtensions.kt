package zzame.commerce.common.util

fun String?.trimToNull(): String? = this
    ?.trim()
    ?.takeIf { it.isNotEmpty() }
