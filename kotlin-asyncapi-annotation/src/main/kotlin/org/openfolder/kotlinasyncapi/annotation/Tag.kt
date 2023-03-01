package org.openfolder.kotlinasyncapi.annotation

annotation class Tag(
    val default: Boolean = false,
    val name: String,
    val description: String = "",
    val externalDocs: ExternalDocumentation = ExternalDocumentation(default = true, url = "")
)
