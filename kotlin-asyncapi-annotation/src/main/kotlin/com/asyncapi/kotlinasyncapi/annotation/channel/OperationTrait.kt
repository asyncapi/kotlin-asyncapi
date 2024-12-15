package com.asyncapi.kotlinasyncapi.annotation.channel

import com.asyncapi.kotlinasyncapi.annotation.ExternalDocumentation
import com.asyncapi.kotlinasyncapi.annotation.Tag

annotation class OperationTrait(
    val isDefault: Boolean = false,
    val operationId: String = "",
    val summary: String = "",
    val description: String = "",
    val tags: Array<Tag> = [],
    val externalDocs: ExternalDocumentation = ExternalDocumentation(isDefault = true, url = ""),
    val bindings: OperationBindings = OperationBindings(isDefault = true)
)
