package org.openfolder.kotlinasyncapi.annotation.channel

import org.openfolder.kotlinasyncapi.annotation.Schema

annotation class Parameters(
    val default: Boolean = false,
    val reference: String = "",
    val description: String = "",
    val location: String = "",
    val schema: Schema = Schema(default = true)
)
