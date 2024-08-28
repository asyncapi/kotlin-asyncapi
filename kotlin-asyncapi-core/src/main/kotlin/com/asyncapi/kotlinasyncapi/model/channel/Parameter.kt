package com.asyncapi.kotlinasyncapi.model.channel

import com.asyncapi.kotlinasyncapi.model.AsyncApiComponent
import com.asyncapi.kotlinasyncapi.model.Reference
import com.asyncapi.kotlinasyncapi.model.Schema

@AsyncApiComponent
class ReferencableParametersMap : LinkedHashMap<String, Any>() {
    inline fun parameter(key: String, build: Parameter.() -> Unit): Parameter =
        Parameter().apply(build).also { put(key, it) }

    inline fun reference(key: String, build: Reference.() -> Unit): Reference =
        Reference().apply(build).also { put(key, it) }
}

@AsyncApiComponent
class Parameter {
    var description: String? = null
    var schema: Any? = null
    var location: String? = null

    fun description(value: String): String =
        value.also { description = it }

    inline fun schema(build: Schema.() -> Unit): Schema =
        Schema().apply(build).also { schema = it }

    inline fun schemaRef(build: Reference.() -> Unit): Reference =
        Reference().apply(build).also { schema = it }

    fun location(value: String): String =
        value.also { location = it }
}
