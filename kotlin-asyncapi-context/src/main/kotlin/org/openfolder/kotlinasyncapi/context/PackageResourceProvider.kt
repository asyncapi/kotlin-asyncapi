package org.openfolder.kotlinasyncapi.context

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import org.openfolder.kotlinasyncapi.model.AsyncApi

class PackageResourceProvider(
    private val path: String
) : AsyncApiContextProvider {

    private val objectMapper = ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)

    override val asyncApi: AsyncApi? by lazy {
        resource(path)?.let { objectMapper.readValue(it, AsyncApi::class.java) }
    }

    private fun resource(path: String): String? =
        javaClass.classLoader.getResource(path)?.readText()
}
