package org.openfolder.kotlinasyncapi.model

@AsyncApiComponent
class ReferencableCorrelationIDsMap : LinkedHashMap<String, Any>() {
    inline fun id(key: String, build: CorrelationID.() -> Unit): CorrelationID =
        CorrelationID().apply(build).also { put(key, it) }

    inline fun reference(key: String, build: Reference.() -> Unit): Reference =
        Reference().apply(build).also { put(key, it) }
}

@AsyncApiComponent
class CorrelationID {
    lateinit var location: String
    var description: String? = null

    fun location(value: String): String =
        value.also { location = it }

    fun description(value: String): String =
        value.also { description = it }
}
