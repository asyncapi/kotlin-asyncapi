package com.asyncapi.kotlinasyncapi.context

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.asyncapi.kotlinasyncapi.model.AsyncApi

class ResourceProvider(path: String) : AsyncApiContextProvider {

    private val objectMapper = ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)

    override val asyncApi: AsyncApi? by lazy {
        resource?.let { objectMapper.readValue(it, AsyncApi::class.java) }
    }

    val resource: String? = javaClass.classLoader.getResource(path)?.readText()
}
