package org.openfolder.kotlinasyncapi.model.info

import org.openfolder.kotlinasyncapi.model.AsyncApiComponent

@AsyncApiComponent
class License {
    lateinit var name: String
    var url: String? = null

    fun name(value: String): String =
        value.also { name = it }

    fun url(value: String): String =
        value.also { url = it }
}
