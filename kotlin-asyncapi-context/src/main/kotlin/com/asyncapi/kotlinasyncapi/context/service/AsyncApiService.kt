package com.asyncapi.kotlinasyncapi.context.service

import com.asyncapi.kotlinasyncapi.model.AsyncApi

interface AsyncApiService {

    /**
     * Supplies the AsyncApi resource
     */
    fun asyncApi(): AsyncApi
}

class DefaultAsyncApiService(
    extensions: List<AsyncApiExtension>
) : AsyncApiService {
    private val asyncApi by lazy {
        AsyncApi().apply {
            extensions.sortedBy { it.order }.forEach { it.extend(this) }
        }
    }

    override fun asyncApi() = asyncApi
}
