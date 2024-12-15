package com.asyncapi.kotlinasyncapi.model.info

import com.asyncapi.kotlinasyncapi.model.AsyncApiComponent

@AsyncApiComponent
class Contact {
    var name: String? = null
    var url: String? = null
    var email: String? = null

    fun name(value: String): String =
        value.also { name = it }

    fun url(value: String): String =
        value.also { url = it }

    fun email(value: String): String =
        value.also { email = it }
}
