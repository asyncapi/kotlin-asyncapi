package org.openfolder.kotlinasyncapi.springweb.service

import org.openfolder.kotlinasyncapi.model.AsyncApi
import org.springframework.stereotype.Service

@Service
internal class DefaultAsyncApiService(
    extension: AsyncApiExtension?
) : AsyncApiService {

    private val asyncApi by lazy { AsyncApi().apply { extension?.extend(this) } }

    override fun asyncApi() = asyncApi
}
