package org.openfolder.kotlinasyncapi.springweb.context

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
internal class DefaultResourceProvider(
    private val context: ApplicationContext
) : ResourceProvider {

    private val objectMapper = ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)

    override fun <T : Any> resource(path: String, clazz: KClass<T>): T? =
        context.getResource(path).file.takeIf { it.exists() }?.let {
            objectMapper.readValue(it, clazz.java)
        }
}
