package com.asyncapi.kotlinasyncapi.springweb

import org.springframework.stereotype.Component

@Component
internal data class AsyncApiProperties(
    val enabled: Boolean = true,
    val path: String = "/docs/asyncapi",
    val script: AsyncApiScriptProperties = AsyncApiScriptProperties()
)

internal data class AsyncApiScriptProperties(
    val enabled: Boolean = true,
    val resourcePath: String = "asyncapi/generated/asyncapi.json",
    val sourcePath: String = "build.asyncapi.kts"
)
