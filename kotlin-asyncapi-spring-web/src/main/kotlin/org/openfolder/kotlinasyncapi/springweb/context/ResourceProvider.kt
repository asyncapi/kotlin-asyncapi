package org.openfolder.kotlinasyncapi.springweb.context

import kotlin.reflect.KClass

internal interface ResourceProvider {

    fun <T : Any> resource(path: String, clazz: KClass<T>): T?
}
