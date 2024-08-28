package com.asyncapi.kotlinasyncapi.context

import com.asyncapi.kotlinasyncapi.model.AsyncApi

interface AsyncApiContextProvider {

    val asyncApi: AsyncApi?
}
