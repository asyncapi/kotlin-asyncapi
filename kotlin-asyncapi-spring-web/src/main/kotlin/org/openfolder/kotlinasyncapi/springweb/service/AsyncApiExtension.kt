package org.openfolder.kotlinasyncapi.springweb.service

import org.openfolder.kotlinasyncapi.model.AsyncApi

fun interface AsyncApiExtension {

    /**
     * Extends the generated AsyncApi resource
     */
    fun extend(asyncApi: AsyncApi): AsyncApi

    companion object {
        fun builder(build: AsyncApi.() -> Unit): AsyncApiExtension =
            AsyncApiExtension { asyncApi -> asyncApi.apply(build) }
    }
}
