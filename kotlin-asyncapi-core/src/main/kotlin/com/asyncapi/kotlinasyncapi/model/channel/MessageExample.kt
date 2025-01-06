package com.asyncapi.kotlinasyncapi.model.channel

import com.asyncapi.kotlinasyncapi.model.AsyncApiComponent

@AsyncApiComponent
class MessageExamplesList : ArrayList<MessageExample>() {
    inline fun example(build: MessageExample.() -> Unit): MessageExample =
        MessageExample().apply(build).also { add(it) }
}

@AsyncApiComponent
class MessageExample {
    var headers: Map<String, Any>? = null
    var payload: Any? = null
    var name: String? = null
    var summary: String? = null

    fun headers(vararg values: Pair<String, Any>): Map<String, Any> =
        mapOf(*values).also { headers = it }

    fun payload(value: Any): Any =
        value.also { payload = it }

    fun name(value: String): String =
        value.also { name = it }

    fun summary(value: String): String =
        value.also { summary = it }
}
