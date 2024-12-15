package com.asyncapi.kotlinasyncapi.model.component

import com.asyncapi.kotlinasyncapi.model.AsyncApiComponent
import com.asyncapi.kotlinasyncapi.model.Reference

@AsyncApiComponent
class ReferencableSecuritySchemasMap : LinkedHashMap<String, Any>() {
    inline fun schema(key: String, build: SecuritySchema.() -> Unit): SecuritySchema =
        SecuritySchema().apply(build).also { put(key, it) }

    inline fun reference(key: String, build: Reference.() -> Unit): Reference =
        Reference().apply(build).also { put(key, it) }
}

@AsyncApiComponent
class SecuritySchema {
    lateinit var type: String
    var name: String? = null
    var `in`: String? = null
    var scheme: String? = null
    var flows: OAuthFlows? = null
    var openIdConnectUrl: String? = null
    var description: String? = null
    var bearerFormat: String? = null

    fun type(value: String): String =
        value.also { type = it }

    fun name(value: String): String =
        value.also { name = it }

    fun `in`(value: String): String =
        value.also { `in` = it }

    fun scheme(value: String): String =
        value.also { scheme = it }

    inline fun flows(build: OAuthFlows.() -> Unit): OAuthFlows =
        OAuthFlows().apply(build).also { flows = it }

    fun openIdConnectUrl(value: String): String =
        value.also { openIdConnectUrl = it }

    fun description(value: String): String =
        value.also { description = it }

    fun bearerFormat(value: String): String =
        value.also { bearerFormat = it }
}
