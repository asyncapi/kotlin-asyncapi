package org.openfolder.kotlinasyncapi.springweb.context

import org.openfolder.kotlinasyncapi.model.AsyncApi

internal interface AsyncApiContextProvider {

    val asyncApi: AsyncApi?
}
