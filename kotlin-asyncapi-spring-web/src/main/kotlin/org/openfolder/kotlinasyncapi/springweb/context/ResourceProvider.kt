package org.openfolder.kotlinasyncapi.springweb.context

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.ApplicationContext
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

internal interface ResourceProvider {

    fun <T : Any> resource(path: String, clazz: KClass<T>): T?

    fun resource(path: String): Resource?
}

@Component
internal class DefaultResourceProvider(
    private val context: ApplicationContext
) : ResourceProvider {

    private val objectMapper = ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)

    override fun <T : Any> resource(path: String, clazz: KClass<T>): T? =
        resource(path)?.let { objectMapper.readValue(it.file, clazz.java) }

    override fun resource(path: String): Resource? =
        context.getResource(path).takeIf { it.exists() }
}
