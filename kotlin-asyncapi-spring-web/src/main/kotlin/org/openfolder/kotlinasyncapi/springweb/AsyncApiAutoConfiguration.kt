package org.openfolder.kotlinasyncapi.springweb

import org.openfolder.kotlinasyncapi.springweb.controller.AsyncApiController
import org.openfolder.kotlinasyncapi.springweb.service.AsyncApiExtension
import org.openfolder.kotlinasyncapi.springweb.service.AsyncApiSerializer
import org.openfolder.kotlinasyncapi.springweb.service.AsyncApiService
import org.openfolder.kotlinasyncapi.springweb.service.DefaultAsyncApiSerializer
import org.openfolder.kotlinasyncapi.springweb.service.DefaultAsyncApiService
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnBean(AsyncApiMarkerConfiguration.Marker::class)
internal open class AsyncApiAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    open fun asyncApiSerializer(): AsyncApiSerializer =
        DefaultAsyncApiSerializer()

    @Bean
    open fun asyncApiService(extension: AsyncApiExtension?): AsyncApiService =
        DefaultAsyncApiService(extension)

    @Bean
    open fun asyncApiController(service: AsyncApiService, serializer: AsyncApiSerializer): AsyncApiController =
        AsyncApiController(service, serializer)
}
