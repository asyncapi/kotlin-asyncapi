package com.asyncapi.kotlinasyncapi.model

import com.asyncapi.kotlinasyncapi.model.channel.ReferencableChannelsMap
import com.asyncapi.kotlinasyncapi.model.component.Components
import com.asyncapi.kotlinasyncapi.model.info.Info
import com.asyncapi.kotlinasyncapi.model.server.ReferencableServersMap
import com.asyncapi.kotlinasyncapi.util.checkInitialized

@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPEALIAS)
@DslMarker
annotation class AsyncApiComponent

@AsyncApiComponent
class AsyncApi {
    val asyncapi = VERSION
    lateinit var info: Info
    lateinit var channels: ReferencableChannelsMap
    var id: String? = null
    var servers: ReferencableServersMap? = null
    var defaultContentType: String? = null
    var components: Components? = null
    var tags: TagsList? = null
    var externalDocs: ExternalDocumentation? = null

    fun id(value: String): String =
        value.also { id = it }

    fun info(build: Info.() -> Unit): Info =
        Info().apply(build).also { info = it }

    fun servers(build: ReferencableServersMap.() -> Unit): ReferencableServersMap =
        ReferencableServersMap().apply(build).also { servers = it }

    fun channels(build: ReferencableChannelsMap.() -> Unit): ReferencableChannelsMap =
        ReferencableChannelsMap().apply(build).also { channels = it }

    fun defaultContentType(value: String): String =
        value.also { defaultContentType = it }

    fun components(build: Components.() -> Unit): Components =
        Components().apply(build).also { components = it }

    fun tags(build: TagsList.() -> Unit): TagsList =
        TagsList().apply(build).also { tags = it }

    fun externalDocs(build: ExternalDocumentation.() -> Unit): ExternalDocumentation =
        ExternalDocumentation().apply(build).also { externalDocs = it }

    internal fun validateForSerialization() {
        checkInitialized(
            "info" to { info },
            "channels" to { channels }
        )
    }

    companion object {
        const val VERSION = "2.4.0"

        fun asyncApi(build: AsyncApi.() -> Unit): AsyncApi =
            AsyncApi().apply(build)
    }
}