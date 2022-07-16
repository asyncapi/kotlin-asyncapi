package org.openfolder.kotlinasyncapi.model.server

import org.openfolder.kotlinasyncapi.model.AsyncApiComponent
import org.openfolder.kotlinasyncapi.model.Reference
import java.util.AbstractMap

@AsyncApiComponent
class ReferencableServersMap : LinkedHashMap<String, Any>() {
    inline fun server(key: String, build: Server.() -> Unit): Server =
        Server().apply(build).also { put(key, it) }

    inline fun reference(key: String, build: Reference.() -> Unit): Reference =
        Reference().apply(build).also { put(key, it) }
}

@AsyncApiComponent
class SecurityRequirementsList : ArrayList<Map.Entry<String, List<String>>>() {
    fun requirement(value: Pair<String, List<String>>): Map.Entry<String, List<String>> =
        value.let { AbstractMap.SimpleEntry(it.first, it.second) }.also { add(it) }
}

@AsyncApiComponent
class Server {
    lateinit var url: String
    lateinit var protocol: String
    var protocolVersion: String? = null
    var description: String? = null
    var variables: ReferencableServerVariablesMap? = null
    var security: SecurityRequirementsList? = null
    var bindings: Any? = null

    fun url(value: String): String =
        value.also { url = it }

    fun protocol(value: String): String =
        value.also { protocol = it }

    fun protocolVersion(value: String): String =
        value.also { protocolVersion = it }

    fun description(value: String): String =
        value.also { description = it }

    inline fun variables(build: ReferencableServerVariablesMap.() -> Unit): ReferencableServerVariablesMap =
        ReferencableServerVariablesMap().apply(build).also { variables = it }

    inline fun security(build: SecurityRequirementsList.() -> Unit): SecurityRequirementsList =
        SecurityRequirementsList().apply(build).also { security = it }

    inline fun bindings(build: ServerBindings.() -> Unit): ServerBindings =
        ServerBindings().apply(build).also { bindings = it }

    inline fun bindingsRef(build: Reference.() -> Unit): Reference =
        Reference().apply(build).also { bindings = it }
}
