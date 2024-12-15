package com.asyncapi.kotlinasyncapi.example.ktor

import com.asyncapi.kotlinasyncapi.context.service.AsyncApiExtension

val asyncApiExtension = AsyncApiExtension.builder {
    defaultContentType("application/json")
    servers {
        server("production") {
            url("https://stream.gitter.im/v1")
            protocol("https")
            protocolVersion("1.1")
        }
    }
}
