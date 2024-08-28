package com.asyncapi.kotlinasyncapi.context.service

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.asyncapi.kotlinasyncapi.model.AsyncApi

fun interface AsyncApiSerializer {

    /**
     * Serializes the AsyncApi resource
     */
    fun AsyncApi.serialize(): String
}

class DefaultAsyncApiSerializer : AsyncApiSerializer {

    private val objectMapper =
        ObjectMapper().registerKotlinModule().setSerializationInclusion(JsonInclude.Include.NON_NULL)

    override fun AsyncApi.serialize(): String = objectMapper.writeValueAsString(this)
}
