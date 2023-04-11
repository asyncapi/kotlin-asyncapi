package org.openfolder.kotlinasyncapi.springweb.context

import org.openfolder.kotlinasyncapi.annotation.AsyncApiAnnotation
import org.openfolder.kotlinasyncapi.annotation.Schema
import org.openfolder.kotlinasyncapi.annotation.channel.Channel
import org.openfolder.kotlinasyncapi.annotation.channel.Message
import org.openfolder.kotlinasyncapi.annotation.channel.Parameter
import org.openfolder.kotlinasyncapi.annotation.channel.Subscribe
import org.openfolder.kotlinasyncapi.model.AsyncApi
import org.openfolder.kotlinasyncapi.model.ReferencableCorrelationIDsMap
import org.openfolder.kotlinasyncapi.model.ReferencableSchemasMap
import org.openfolder.kotlinasyncapi.model.channel.ReferencableChannelBindingsMap
import org.openfolder.kotlinasyncapi.model.channel.ReferencableChannelsMap
import org.openfolder.kotlinasyncapi.model.channel.ReferencableMessageBindingsMap
import org.openfolder.kotlinasyncapi.model.channel.ReferencableMessageTraitsMap
import org.openfolder.kotlinasyncapi.model.channel.ReferencableMessagesMap
import org.openfolder.kotlinasyncapi.model.channel.ReferencableOperationBindingsMap
import org.openfolder.kotlinasyncapi.model.channel.ReferencableOperationTraitsMap
import org.openfolder.kotlinasyncapi.model.channel.ReferencableParametersMap
import org.openfolder.kotlinasyncapi.model.component.Components
import org.openfolder.kotlinasyncapi.model.component.ReferencableSecuritySchemasMap
import org.openfolder.kotlinasyncapi.model.server.ReferencableServerBindingsMap
import org.openfolder.kotlinasyncapi.model.server.ReferencableServerVariablesMap
import org.openfolder.kotlinasyncapi.model.server.ReferencableServersMap
import org.openfolder.kotlinasyncapi.springweb.EnableAsyncApi
import org.openfolder.kotlinasyncapi.springweb.context.annotation.AnnotationScanner
import org.openfolder.kotlinasyncapi.springweb.context.annotation.processor.AnnotationProcessor
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation

@Component
@Schema
internal class AnnotationProvider(
    private val context: ApplicationContext,
    private val scanner: AnnotationScanner,
    private val messageProcessor: AnnotationProcessor<Message, KClass<*>>,
    private val schemaProcessor: AnnotationProcessor<Schema, KClass<*>>,
    private val channelProcessor: AnnotationProcessor<Channel, KClass<*>>
) : AsyncApiContextProvider {

    private val componentToChannelMapping = mutableMapOf<String, String>()

    override val asyncApi: AsyncApi? by lazy {
        AsyncApi().apply {
            components {
                bind(this)
            }
            channels {
                bind(this)
            }
        }
    }

    private fun bind(channels: ReferencableChannelsMap) {
        for ((component, channel) in componentToChannelMapping) {
            channels.reference(channel) {
                ref("#/components/channels/$component")
            }
        }
    }

    private fun bind(components: Components) {
        val scanPackage = context.getBeansWithAnnotation(EnableAsyncApi::class.java).values
            .firstOrNull()
            ?.let { it::class.java.`package`.name }
            ?.takeIf { it.isNotEmpty() }

        val annotatedClasses = scanPackage?.let {
            scanner.scan(scanPackage = it, annotation = AsyncApiAnnotation::class)
        } ?: emptyList()

        annotatedClasses
            .flatMap { clazz ->
                listOfNotNull(
                    clazz.findAnnotation<Message>()?.let { clazz to it },
                    clazz.findAnnotation<Schema>()?.let { clazz to it },
                    clazz.findAnnotation<Channel>()?.let { clazz to it }
                )
            }
            .mapNotNull { (clazz, annotation) ->
                when (annotation) {
                    is Message -> messageProcessor.process(annotation, clazz)
                    is Schema -> schemaProcessor.process(annotation, clazz)
                    is Channel -> channelProcessor.process(annotation, clazz).also {
                        componentToChannelMapping[clazz.java.simpleName] =
                            annotation.value.takeIf { it.isNotEmpty() } ?: clazz.java.simpleName
                    }
                    else -> null
                }
            }
            .forEach { components + it }
    }

    private operator fun Components.plus(components: Components) {
        components.schemas?.also {
            schemas = (schemas ?: ReferencableSchemasMap()).apply { putAll(it) }
        }
        components.servers?.also {
            servers = (servers ?: ReferencableServersMap()).apply { putAll(it) }
        }
        components.serverVariables?.also {
            serverVariables = (serverVariables ?: ReferencableServerVariablesMap()).apply { putAll(it) }
        }
        components.channels?.also {
            channels = (channels ?: ReferencableChannelsMap()).apply { putAll(it) }
        }
        components.messages?.also {
            messages = (messages ?: ReferencableMessagesMap()).apply { putAll(it) }
        }
        components.securitySchemes?.also {
            securitySchemes = (securitySchemes ?: ReferencableSecuritySchemasMap()).apply { putAll(it) }
        }
        components.parameters?.also {
            parameters = (parameters ?: ReferencableParametersMap()).apply { putAll(it) }
        }
        components.correlationIds?.also {
            correlationIds = (correlationIds ?: ReferencableCorrelationIDsMap()).apply { putAll(it) }
        }
        components.operationTraits?.also {
            operationTraits = (operationTraits ?: ReferencableOperationTraitsMap()).apply { putAll(it) }
        }
        components.messageTraits?.also {
            messageTraits = (messageTraits ?: ReferencableMessageTraitsMap()).apply { putAll(it) }
        }
        components.serverBindings?.also {
            serverBindings = (serverBindings ?: ReferencableServerBindingsMap()).apply { putAll(it) }
        }
        components.channelBindings?.also {
            channelBindings = (channelBindings ?: ReferencableChannelBindingsMap()).apply { putAll(it) }
        }
        components.operationBindings?.also {
            operationBindings = (operationBindings ?: ReferencableOperationBindingsMap()).apply { putAll(it) }
        }
        components.messageBindings?.also {
            messageBindings = (messageBindings ?: ReferencableMessageBindingsMap()).apply { putAll(it) }
        }
    }
}
