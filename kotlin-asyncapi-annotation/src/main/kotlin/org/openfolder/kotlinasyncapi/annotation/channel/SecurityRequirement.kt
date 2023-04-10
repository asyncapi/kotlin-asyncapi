package org.openfolder.kotlinasyncapi.annotation.channel

annotation class SecurityRequirement(
    val key: String = "",
    val isDefault: Boolean = false,
    val values: Array<String> = []
)
