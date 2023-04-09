package org.openfolder.kotlinasyncapi.annotation.channel

import org.openfolder.kotlinasyncapi.annotation.ExternalDocumentation
import org.openfolder.kotlinasyncapi.annotation.Tag

annotation class OperationTrait(
    val default: Boolean = false,
    val operationId: String = "",
    val summary: String = "",
    val description: String = "",
    val tags: Array<Tag> = [],
    val externalDocs: ExternalDocumentation = ExternalDocumentation(default = true, url = ""),
    val bindings: OperationBindings = OperationBindings(default = true)
)
