package org.openfolder.kotlinasyncapi.annotation.channel

import org.openfolder.kotlinasyncapi.annotation.Schema

annotation class Parameter(
    val value: String = "",
    val default: Boolean = false,
    val description: String = "",
    val location: String = "",
    val schema: Schema = Schema(default = true)
)
