package org.openfolder.kotlinasyncapi.example.spring

import org.openfolder.kotlinasyncapi.springweb.service.AsyncApiExtension
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
internal class AsyncApiConfiguration {

    @Bean
    fun asyncApiExtension() = AsyncApiExtension.builder {
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
}
