package org.openfolder.kotlinasyncapi.springweb

import org.openfolder.kotlinasyncapi.annotation.Schema
import org.openfolder.kotlinasyncapi.annotation.channel.Channel
import org.openfolder.kotlinasyncapi.annotation.channel.Message
import org.openfolder.kotlinasyncapi.springweb.context.AnnotationProvider
import org.openfolder.kotlinasyncapi.springweb.context.AsyncApiContextProvider
import org.openfolder.kotlinasyncapi.springweb.context.PackageInfoProvider
import org.openfolder.kotlinasyncapi.springweb.context.PackageResourceProvider
import org.openfolder.kotlinasyncapi.springweb.context.annotation.AnnotationScanner
import org.openfolder.kotlinasyncapi.springweb.context.annotation.DefaultAnnotationScanner
import org.openfolder.kotlinasyncapi.springweb.context.annotation.processor.AnnotationProcessor
import org.openfolder.kotlinasyncapi.springweb.context.annotation.processor.ChannelProcessor
import org.openfolder.kotlinasyncapi.springweb.context.annotation.processor.MessageProcessor
import org.openfolder.kotlinasyncapi.springweb.context.annotation.processor.SchemaProcessor
import org.openfolder.kotlinasyncapi.springweb.controller.AsyncApiController
import org.openfolder.kotlinasyncapi.springweb.service.AsyncApiExtension
import org.openfolder.kotlinasyncapi.springweb.service.AsyncApiSerializer
import org.openfolder.kotlinasyncapi.springweb.service.AsyncApiService
import org.openfolder.kotlinasyncapi.springweb.service.DefaultAsyncApiSerializer
import org.openfolder.kotlinasyncapi.springweb.service.DefaultAsyncApiService
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import kotlin.reflect.KClass
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost

@Configuration
@ConditionalOnBean(AsyncApiMarkerConfiguration.Marker::class)
@Import(AsyncApiScriptAutoConfiguration::class, AsyncApiAnnotationAutoConfiguration::class)
internal open class AsyncApiAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    open fun asyncApiSerializer(): AsyncApiSerializer =
        DefaultAsyncApiSerializer()

    @Bean
    open fun packageInfoProvider(context: ApplicationContext) =
        PackageInfoProvider(context)

    @Bean
    @ConfigurationProperties(prefix = "asyncapi", ignoreUnknownFields = false)
    open fun asyncApiProperties() =
        AsyncApiProperties()

    @Bean
    open fun asyncApiPackageInfoExtension(packageInfoProvider: AsyncApiContextProvider) =
        packageInfoProvider.asyncApi?.let { AsyncApiExtension.from(order = -1, it) }

    @Bean
    open fun asyncApiDefaultChannelsExtension() =
        AsyncApiExtension.builder(order = -1) {
            channels { }
        }

    @Bean
    open fun asyncApiService(extensions: List<AsyncApiExtension>): AsyncApiService =
        DefaultAsyncApiService(extensions)

    @Bean
    open fun asyncApiController(service: AsyncApiService, serializer: AsyncApiSerializer): AsyncApiController =
        AsyncApiController(service, serializer)
}

@Configuration
@ConditionalOnProperty(name = ["asyncapi.script.enabled"], havingValue = "true", matchIfMissing = true)
@Import(AsyncApiEmbeddedScriptAutoConfiguration::class)
internal open class AsyncApiScriptAutoConfiguration {

    @Bean
    open fun packageResourceProvider(context: ApplicationContext, asyncApiProperties: AsyncApiProperties) =
        PackageResourceProvider(context, asyncApiProperties.script.resourcePath)

    @Bean
    open fun asyncApiPackageResourceExtension(packageResourceProvider: AsyncApiContextProvider) =
        packageResourceProvider.asyncApi?.let { AsyncApiExtension.from(order = -1, it) }
}

@Configuration
@ConditionalOnProperty(name = ["asyncapi.annotation.enabled"], havingValue = "true", matchIfMissing = true)
internal open class AsyncApiAnnotationAutoConfiguration {

    @Bean
    open fun messageProcessor() =
        MessageProcessor()

    @Bean
    open fun schemaProcessor() =
        SchemaProcessor()

    @Bean
    open fun channelProcessor() =
        ChannelProcessor()

    @Bean
    open fun annotationScanner(context: ApplicationContext) =
        DefaultAnnotationScanner(context)

    @Bean
    open fun annotationProvider(
        context: ApplicationContext,
        scanner: AnnotationScanner,
        messageProcessor: AnnotationProcessor<Message, KClass<*>>,
        schemaProcessor: AnnotationProcessor<Schema, KClass<*>>,
        channelProcessor: AnnotationProcessor<Channel, KClass<*>>
    ) = AnnotationProvider(context, scanner, messageProcessor, schemaProcessor, channelProcessor)
}

@Configuration
@ConditionalOnClass(BasicJvmScriptingHost::class)
internal open class AsyncApiEmbeddedScriptAutoConfiguration {

    @Bean
    open fun asyncApiScriptExtension(context: ApplicationContext, asyncApiProperties: AsyncApiProperties) =
        context.getResource(asyncApiProperties.script.sourcePath).takeIf { it.exists() }?.let {
            AsyncApiExtension.from(
                script = it.inputStream.bufferedReader().use { it.readText() }.toScriptSource()
            )
        }
}

@Configuration
internal open class AsyncApiMarkerConfiguration {

    @Bean
    open fun asyncApiMarkerBean(): Marker {
        return Marker()
    }

    class Marker
}
