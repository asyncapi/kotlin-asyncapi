package org.openfolder.kotlinasyncapi.annotation

annotation class Tag(
    val isDefault: kotlin.Boolean = false,
    val name: String,
    val description: String = "",
    val externalDocs: ExternalDocumentation = ExternalDocumentation(isDefault = true, url = "")
)
