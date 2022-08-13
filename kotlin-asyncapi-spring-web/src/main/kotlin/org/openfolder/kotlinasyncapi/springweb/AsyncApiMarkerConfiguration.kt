package org.openfolder.kotlinasyncapi.springweb

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
internal open class AsyncApiMarkerConfiguration {

    @Bean
    open fun asyncApiMarkerBean(): Marker {
        return Marker()
    }

    class Marker
}
