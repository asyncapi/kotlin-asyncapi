package org.openfolder.kotlinasyncapi.annotation.channel

import org.openfolder.kotlinasyncapi.annotation.Schema

annotation class MessageExample(
    val default: Boolean = false,
    val headers: String = "",
    val payload: String = "",
    val name: String = "",
    val summary: String = ""
)
