package com.asyncapi.kotlinasyncapi.annotation.channel

annotation class MessageExample(
    val isDefault: Boolean = false,
    val headers: String = "",
    val payload: String = "",
    val name: String = "",
    val summary: String = ""
)
