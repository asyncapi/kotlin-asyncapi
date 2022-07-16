package org.openfolder.kotlinasyncapi.model.server

import org.openfolder.kotlinasyncapi.model.AsyncApiComponent
import org.openfolder.kotlinasyncapi.model.Reference

@AsyncApiComponent
class ReferencableServerVariablesMap : LinkedHashMap<String, Any>() {
    inline fun variable(key: String, build: ServerVariable.() -> Unit): ServerVariable =
        ServerVariable().apply(build).also { put(key, it) }

    inline fun reference(key: String, build: Reference.() -> Unit): Reference =
        Reference().apply(build).also { put(key, it) }
}

@AsyncApiComponent
class ServerVariable {
    var enum: List<String>? = null
    var default: String? = null
    var description: String? = null
    var examples: List<String>? = null

    fun enum(vararg values: String): List<String> =
        listOf(*values).also { enum = it }

    fun default(value: String): String =
        value.also { default = it }

    fun description(value: String): String =
        value.also { description = it }

    fun examples(vararg values: String): List<String> =
        listOf(*values).also { examples = it }
}
