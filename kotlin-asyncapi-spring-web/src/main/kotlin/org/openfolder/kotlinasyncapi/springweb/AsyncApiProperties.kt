package org.openfolder.kotlinasyncapi.springweb

import org.springframework.stereotype.Component

@Component
internal data class AsyncApiProperties(
    val enabled: Boolean = true,
    val path: String = "/docs/asyncapi",
    val script: AsyncApiScriptProperties = AsyncApiScriptProperties()
)

internal data class AsyncApiScriptProperties(
    val enabled: Boolean = true,
    val resourcePath: String = "classpath:asyncapi/generated/asyncapi.json",
    val sourcePath: String = "classpath:build.asyncapi.kts"
)
