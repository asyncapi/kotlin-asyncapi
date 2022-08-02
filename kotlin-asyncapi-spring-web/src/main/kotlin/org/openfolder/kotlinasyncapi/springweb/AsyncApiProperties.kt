package org.openfolder.kotlinasyncapi.springweb

import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.stereotype.Component

@Component
@ConstructorBinding
internal data class AsyncApiProperties(
    val enabled: Boolean = true,
    val path: String = "/docs/asyncapi",
    val script: AsyncApiScriptProperties = AsyncApiScriptProperties()
)

internal data class AsyncApiScriptProperties(
    val enabled: Boolean = true,
    val path: String = "asyncapi/generated/asyncapi.json"
)
