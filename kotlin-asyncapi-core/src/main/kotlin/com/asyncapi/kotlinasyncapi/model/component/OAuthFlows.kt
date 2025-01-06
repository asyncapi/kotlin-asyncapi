package com.asyncapi.kotlinasyncapi.model.component

import com.asyncapi.kotlinasyncapi.model.AsyncApiComponent

@AsyncApiComponent
class OAuthFlows {
    var implicit: OAuthFlow? = null
    var password: OAuthFlow? = null
    var clientCredentials: OAuthFlow? = null
    var authorizationCode: OAuthFlow? = null

    inline fun implicit(build: OAuthFlow.() -> Unit): OAuthFlow =
        OAuthFlow().apply(build).also { implicit = it }

    inline fun password(build: OAuthFlow.() -> Unit): OAuthFlow =
        OAuthFlow().apply(build).also { password = it }

    inline fun clientCredentials(build: OAuthFlow.() -> Unit): OAuthFlow =
        OAuthFlow().apply(build).also { clientCredentials = it }

    inline fun authorizationCode(build: OAuthFlow.() -> Unit): OAuthFlow =
        OAuthFlow().apply(build).also { authorizationCode = it }
}

@AsyncApiComponent
class OAuthFlow {
    var authorizationUrl: String? = null
    var tokenUrl: String? = null
    var scopes: Map<String, String>? = null
    var refreshUrl: String? = null

    fun authorizationUrl(value: String): String =
        value.also { authorizationUrl = it }

    fun tokenUrl(value: String): String =
        value.also { tokenUrl = it }

    fun scopes(vararg values: Pair<String, String>): Map<String, String> =
        mapOf(*values).also { scopes = it }

    fun refreshUrl(value: String): String =
        value.also { refreshUrl = it }
}
