package org.openfolder.kotlinasyncapi.annotation

annotation class CorrelationID(
    val default: Boolean = false,
    val location: String,
    val description: String = ""
)
