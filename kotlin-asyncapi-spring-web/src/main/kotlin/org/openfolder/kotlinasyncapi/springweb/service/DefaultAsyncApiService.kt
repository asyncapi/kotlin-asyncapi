package org.openfolder.kotlinasyncapi.springweb.service

import org.openfolder.kotlinasyncapi.model.AsyncApi
import org.springframework.stereotype.Service

@Service
internal class DefaultAsyncApiService(
    extensions: List<AsyncApiExtension>
) : AsyncApiService {
    private val asyncApi by lazy {
        AsyncApi().apply {
            extensions.sortedBy { it.order }.forEach { it.extend(this) }
        }
    }

    override fun asyncApi() = asyncApi
}
