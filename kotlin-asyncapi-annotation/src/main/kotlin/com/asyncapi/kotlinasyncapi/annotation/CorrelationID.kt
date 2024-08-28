package com.asyncapi.kotlinasyncapi.annotation

annotation class CorrelationID(
    val isDefault: kotlin.Boolean = false,
    val location: String,
    val description: String = ""
)
