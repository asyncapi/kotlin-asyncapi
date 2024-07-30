package org.openfolder.kotlinasyncapi.ktor

import org.openfolder.kotlinasyncapi.context.service.AsyncApiExtension
import kotlin.reflect.KClass

class AsyncApiConfiguration {
    var path: String = "/docs/asyncapi"
    var baseClass: KClass<*>? = null
    var scanAnnotations: Boolean = true
    var extension: AsyncApiExtension = AsyncApiExtension.empty()
    var extensions: List<AsyncApiExtension> = emptyList()
    var resourcePath: String = "asyncapi/generated/asyncapi.json"
    var sourcePath: String = "build.asyncapi.kts"
}
