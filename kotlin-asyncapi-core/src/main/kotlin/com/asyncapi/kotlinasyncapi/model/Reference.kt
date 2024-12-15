package com.asyncapi.kotlinasyncapi.model

@AsyncApiComponent
class Reference {
    lateinit var `$ref`: String

    fun ref(value: String): String =
        value.also { `$ref` = it }
}
