package org.openfolder.kotlinasyncapi.springweb.service

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import org.openfolder.kotlinasyncapi.model.AsyncApi
import org.springframework.stereotype.Component

fun interface AsyncApiSerializer {

    /**
     * Serializes the AsyncApi resource
     */
    fun AsyncApi.serialize(): String
}

@Component
internal class DefaultAsyncApiSerializer : AsyncApiSerializer {

    private val objectMapper = ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)

    override fun AsyncApi.serialize(): String = objectMapper.writeValueAsString(this)
}
