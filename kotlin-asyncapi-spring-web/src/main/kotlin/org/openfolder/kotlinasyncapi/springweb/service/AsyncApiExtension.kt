package org.openfolder.kotlinasyncapi.springweb.service

import org.openfolder.kotlinasyncapi.model.AsyncApi

interface AsyncApiExtension {

    /**
     * Extensions with higher values overwrite extensions with lower values.
     */
    val order: Int

    /**
     * Extends the generated AsyncApi resource
     */
    fun extend(asyncApi: AsyncApi): AsyncApi

    companion object {
        fun builder(order: Int = 0, build: AsyncApi.() -> Unit): AsyncApiExtension =
            object : AsyncApiExtension {
                override val order = order
                override fun extend(asyncApi: AsyncApi) = asyncApi.apply(build)
            }
    }
}
