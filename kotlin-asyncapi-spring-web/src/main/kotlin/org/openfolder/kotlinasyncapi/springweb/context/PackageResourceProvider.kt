package org.openfolder.kotlinasyncapi.springweb.context

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import org.openfolder.kotlinasyncapi.model.AsyncApi
import org.springframework.context.ApplicationContext
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component

@Component
internal class PackageResourceProvider(
    private val context: ApplicationContext,
    private val path: String
) : AsyncApiContextProvider {

    private val objectMapper = ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)

    override val asyncApi: AsyncApi? by lazy {
        resource(path)?.let { objectMapper.readValue(it.inputStream, AsyncApi::class.java) }
    }

    private fun resource(path: String): Resource? =
        context.getResource(path).takeIf { it.exists() }
}
