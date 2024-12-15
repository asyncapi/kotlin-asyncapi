package com.asyncapi.kotlinasyncapi.annotation.channel

import com.asyncapi.kotlinasyncapi.annotation.Schema

annotation class Parameter(
    val value: String = "",
    val isDefault: Boolean = false,
    val description: String = "",
    val location: String = "",
    val schema: Schema = Schema(isDefault = true)
)
