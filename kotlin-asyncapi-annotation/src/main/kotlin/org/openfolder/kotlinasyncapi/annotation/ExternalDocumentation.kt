package org.openfolder.kotlinasyncapi.annotation

annotation class ExternalDocumentation(
    val default: Boolean = false,
    val url: String,
    val description: String = ""
)
