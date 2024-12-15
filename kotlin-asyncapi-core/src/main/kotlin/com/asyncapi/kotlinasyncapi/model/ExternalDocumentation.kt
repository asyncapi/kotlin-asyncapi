package com.asyncapi.kotlinasyncapi.model

@AsyncApiComponent
class ExternalDocumentation {
    lateinit var url: String
    var description: String? = null

    fun url(value: String): String =
        value.also { url = it }

    fun description(value: String): String =
        value.also { description = it }
}
