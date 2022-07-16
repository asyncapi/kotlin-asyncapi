package org.openfolder.kotlinasyncapi.model.channel

import org.openfolder.kotlinasyncapi.model.AsyncApiComponent
import org.openfolder.kotlinasyncapi.model.Reference

@AsyncApiComponent
class ReferencableChannelsMap : LinkedHashMap<String, Any>() {
    inline fun channel(key: String, build: Channel.() -> Unit): Channel =
        Channel().apply(build).also { put(key, it) }

    inline fun reference(key: String, build: Reference.() -> Unit): Reference =
        Reference().apply(build).also { put(key, it) }
}

@AsyncApiComponent
class Channel {
    var `$ref`: String? = null
    var description: String? = null
    var servers: List<String>? = null
    var subscribe: Operation? = null
    var publish: Operation? = null
    var parameters: ReferencableParametersMap? = null
    var bindings: Any? = null

    @Deprecated("Usage of the \$ref property has been deprecated")
    fun ref(value: String): String =
        value.also { `$ref` = it }

    fun description(value: String): String =
        value.also { description = it }

    fun servers(vararg values: String): List<String> =
        listOf(*values).also { servers = it }

    inline fun subscribe(build: Operation.() -> Unit): Operation =
        Operation().apply(build).also { subscribe = it }

    inline fun publish(build: Operation.() -> Unit): Operation =
        Operation().apply(build).also { publish = it }

    inline fun parameters(build: ReferencableParametersMap.() -> Unit): ReferencableParametersMap =
        ReferencableParametersMap().apply(build).also { parameters = it }

    inline fun bindings(build: ChannelBindings.() -> Unit): ChannelBindings =
        ChannelBindings().apply(build).also { bindings = it }

    inline fun bindingsRef(build: Reference.() -> Unit): Reference =
        Reference().apply(build).also { bindings = it }
}
