package com.asyncapi.kotlinasyncapi.annotation

annotation class ExternalDocumentation(
    val isDefault: kotlin.Boolean = false,
    val url: String,
    val description: String = ""
)
