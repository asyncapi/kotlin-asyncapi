package org.openfolder.kotlinasyncapi.context

import org.openfolder.kotlinasyncapi.model.AsyncApi

interface AsyncApiContextProvider {

    val asyncApi: AsyncApi?
}
