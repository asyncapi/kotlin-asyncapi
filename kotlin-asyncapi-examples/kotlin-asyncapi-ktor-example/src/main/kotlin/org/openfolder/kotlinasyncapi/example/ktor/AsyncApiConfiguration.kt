package org.openfolder.kotlinasyncapi.example.ktor

import org.openfolder.kotlinasyncapi.context.service.AsyncApiExtension

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
