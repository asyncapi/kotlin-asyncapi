package org.openfolder.kotlinasyncapi.model.info

import org.openfolder.kotlinasyncapi.model.AsyncApiComponent

@AsyncApiComponent
class Info {
    lateinit var title: String
    lateinit var version: String
    var description: String? = null
    var termsOfService: String? = null
    var contact: Contact? = null
    var license: License? = null

    fun title(value: String): String =
        value.also { title = it }

    fun version(value: String): String =
        value.also { version = it }

    fun description(value: String): String =
        value.also { description = it }

    fun termsOfService(value: String): String =
        value.also { termsOfService = it }

    inline fun contact(build: Contact.() -> Unit): Contact =
        Contact().apply(build).also { contact = it }

    inline fun license(build: License.() -> Unit): License =
        License().apply(build).also { license = it }
}
