package org.openfolder.kotlinasyncapi.springweb.service

import org.openfolder.kotlinasyncapi.model.AsyncApi

fun interface AsyncApiSerializer {

    /**
     * Serializes the AsyncApi resource
     */
    fun AsyncApi.serialize(): String
}
