package org.openfolder.kotlinasyncapi.springweb

import kotlin.reflect.KClass
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost
import org.openfolder.kotlinasyncapi.annotation.Schema
import org.openfolder.kotlinasyncapi.annotation.channel.Channel
import org.openfolder.kotlinasyncapi.annotation.channel.Message
import org.openfolder.kotlinasyncapi.context.AsyncApiContextProvider
import org.openfolder.kotlinasyncapi.context.PackageInfoProvider
import org.openfolder.kotlinasyncapi.context.ResourceProvider
import org.openfolder.kotlinasyncapi.context.annotation.AnnotationProvider
import org.openfolder.kotlinasyncapi.context.annotation.AnnotationScanner
import org.openfolder.kotlinasyncapi.context.annotation.DefaultAnnotationScanner
import org.openfolder.kotlinasyncapi.context.annotation.processor.AnnotationProcessor
import org.openfolder.kotlinasyncapi.context.annotation.processor.ChannelProcessor
import org.openfolder.kotlinasyncapi.context.annotation.processor.MessageProcessor
import org.openfolder.kotlinasyncapi.context.annotation.processor.SchemaProcessor
import org.openfolder.kotlinasyncapi.context.service.AsyncApiExtension
import org.openfolder.kotlinasyncapi.context.service.AsyncApiSerializer
import org.openfolder.kotlinasyncapi.context.service.AsyncApiService
import org.openfolder.kotlinasyncapi.context.service.DefaultAsyncApiSerializer
import org.openfolder.kotlinasyncapi.context.service.DefaultAsyncApiService
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@AutoConfiguration
@ConditionalOnBean(AsyncApiMarkerConfiguration.Marker::class)
@Import(AsyncApiScriptAutoConfiguration::class, AsyncApiAnnotationAutoConfiguration::class)
internal open class AsyncApiAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    open fun asyncApiSerializer(): AsyncApiSerializer =
        DefaultAsyncApiSerializer()

    @Bean
    open fun packageInfoProvider(context: ApplicationContext) =
        packageFromContext(context)?.let {
            PackageInfoProvider(applicationPackage = it)
        }

    @Bean
    @ConfigurationProperties(prefix = "asyncapi", ignoreUnknownFields = false)
    open fun asyncApiProperties() =
        AsyncApiProperties()

    @Bean
    open fun asyncApiPackageInfoExtension(packageInfoProvider: AsyncApiContextProvider) =
        packageInfoProvider.asyncApi?.let { AsyncApiExtension.from(order = -100, it) }

    @Bean
    open fun asyncApiDefaultChannelsExtension() =
        AsyncApiExtension.builder(order = -100) {
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
    open fun packageResourceProvider(asyncApiProperties: AsyncApiProperties) =
        ResourceProvider(path = asyncApiProperties.script.resourcePath)

    @Bean
    open fun asyncApiPackageResourceExtension(packageResourceProvider: AsyncApiContextProvider) =
        packageResourceProvider.asyncApi?.let { AsyncApiExtension.from(order = -10, resource = it) }
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
    open fun annotationScanner() =
        DefaultAnnotationScanner()

    @Bean
    open fun annotationProvider(
        context: ApplicationContext,
        scanner: AnnotationScanner,
        messageProcessor: AnnotationProcessor<Message, KClass<*>>,
        schemaProcessor: AnnotationProcessor<Schema, KClass<*>>,
        channelProcessor: AnnotationProcessor<Channel, KClass<*>>
    ) = packageFromContext(context)?.let {
        AnnotationProvider(
            applicationPackage = it,
            scanner = scanner,
            messageProcessor = messageProcessor,
            schemaProcessor = schemaProcessor,
            channelProcessor = channelProcessor,
        )
    }

    @Bean
    open fun asyncApiAnnotationExtension(annotationProvider: AsyncApiContextProvider?) =
        annotationProvider?.asyncApi?.let { AsyncApiExtension.from(order = -1, it) }
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
@ConditionalOnProperty(name = ["asyncapi.enabled"], havingValue = "true", matchIfMissing = true)
internal open class AsyncApiMarkerConfiguration {

    @Bean
    open fun asyncApiMarkerBean(): Marker {
        return Marker()
    }

    class Marker
}

private fun packageFromContext(context: ApplicationContext) =
    context.getBeansWithAnnotation(EnableAsyncApi::class.java)
        .values
        .firstOrNull()
        ?.let { it::class.java.`package` }
