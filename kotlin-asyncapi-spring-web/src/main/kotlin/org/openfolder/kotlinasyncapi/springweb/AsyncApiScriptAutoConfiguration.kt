package org.openfolder.kotlinasyncapi.springweb

import org.openfolder.kotlinasyncapi.model.AsyncApi
import org.openfolder.kotlinasyncapi.springweb.context.DefaultResourceProvider
import org.openfolder.kotlinasyncapi.springweb.context.ResourceProvider
import org.openfolder.kotlinasyncapi.springweb.service.AsyncApiExtension
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost

@Configuration
@ConditionalOnProperty(name = ["asyncapi.script.enabled"], havingValue = "true", matchIfMissing = true)
internal open class AsyncApiScriptAutoConfiguration {

    @Bean
    open fun resourceProvider(context: ApplicationContext) =
        DefaultResourceProvider(context)

    @Bean
    open fun asyncApiResourceExtension(resourceProvider: ResourceProvider, asyncApiProperties: AsyncApiProperties) =
        resourceProvider.resource(asyncApiProperties.script.resourcePath, AsyncApi::class)?.let {
            AsyncApiExtension.from(resource = it)
        }

    @Bean
    @ConditionalOnClass(BasicJvmScriptingHost::class)
    open fun asyncApiScriptExtension(resourceProvider: ResourceProvider, asyncApiProperties: AsyncApiProperties) =
        resourceProvider.resource(asyncApiProperties.script.sourcePath)?.let {
            AsyncApiExtension.from(
                script = it.inputStream.bufferedReader().use { it.readText() }.toScriptSource()
            )
        }
}
