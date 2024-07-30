package org.openfolder.kotlinasyncapi.example.ktor

import org.openfolder.kotlinasyncapi.context.service.AsyncApiExtension

val asyncApiExtension = AsyncApiExtension.builder {
    info {
        title("Gitter Streaming API")
        version("1.0.0")
    }
    servers {
        server("production") {
            url("https://stream.gitter.im/v1")
            protocol("https")
            protocolVersion("1.1")
        }
    }
}
