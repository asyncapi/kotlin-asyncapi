package org.openfolder.kotlinasyncapi.annotation

annotation class Int(
    val value: kotlin.Int = 0,
    val isDefault: kotlin.Boolean = false
)

annotation class Boolean(
    val value: kotlin.Boolean = false,
    val isDefault: kotlin.Boolean = false
)
