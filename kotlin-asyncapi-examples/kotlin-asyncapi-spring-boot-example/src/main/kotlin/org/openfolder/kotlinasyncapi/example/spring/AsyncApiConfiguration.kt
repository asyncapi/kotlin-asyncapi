package org.openfolder.kotlinasyncapi.example.spring

import org.openfolder.kotlinasyncapi.context.service.AsyncApiExtension
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
internal class AsyncApiConfiguration {

    @Bean
    fun asyncApiExtension() = AsyncApiExtension.builder {
        defaultContentType("application/json")
        servers {
            server("production") {
                url("https://stream.gitter.im/v1")
                protocol("https")
                protocolVersion("1.1")
            }
        }
    }
}
