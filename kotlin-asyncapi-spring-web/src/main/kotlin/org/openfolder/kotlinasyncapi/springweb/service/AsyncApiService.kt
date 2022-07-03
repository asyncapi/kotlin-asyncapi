package org.openfolder.kotlinasyncapi.springweb.service

import org.openfolder.kotlinasyncapi.model.AsyncApi

internal interface AsyncApiService {

    /**
     * Supplies the AsyncApi resource
     */
    fun asyncApi(): AsyncApi
}
