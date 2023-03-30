package org.openfolder.kotlinasyncapi.annotation.channel

annotation class SecurityRequirement(
    val default: Boolean = false,
    val key: String = "",
    val values: Array<String> = []
)
