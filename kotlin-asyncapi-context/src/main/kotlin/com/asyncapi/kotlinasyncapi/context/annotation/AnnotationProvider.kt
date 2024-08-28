package com.asyncapi.kotlinasyncapi.context.annotation

import com.asyncapi.kotlinasyncapi.annotation.AsyncApiAnnotation
import com.asyncapi.kotlinasyncapi.annotation.Schema
import com.asyncapi.kotlinasyncapi.annotation.channel.Channel
import com.asyncapi.kotlinasyncapi.annotation.channel.Message
import com.asyncapi.kotlinasyncapi.context.AsyncApiContextProvider
import com.asyncapi.kotlinasyncapi.context.annotation.processor.AnnotationProcessor
import com.asyncapi.kotlinasyncapi.model.AsyncApi
import com.asyncapi.kotlinasyncapi.model.ReferencableCorrelationIDsMap
import com.asyncapi.kotlinasyncapi.model.ReferencableSchemasMap
import com.asyncapi.kotlinasyncapi.model.channel.ReferencableChannelBindingsMap
import com.asyncapi.kotlinasyncapi.model.channel.ReferencableChannelsMap
import com.asyncapi.kotlinasyncapi.model.channel.ReferencableMessageBindingsMap
import com.asyncapi.kotlinasyncapi.model.channel.ReferencableMessageTraitsMap
import com.asyncapi.kotlinasyncapi.model.channel.ReferencableMessagesMap
import com.asyncapi.kotlinasyncapi.model.channel.ReferencableOperationBindingsMap
import com.asyncapi.kotlinasyncapi.model.channel.ReferencableOperationTraitsMap
import com.asyncapi.kotlinasyncapi.model.channel.ReferencableParametersMap
import com.asyncapi.kotlinasyncapi.model.component.Components
import com.asyncapi.kotlinasyncapi.model.component.ReferencableSecuritySchemasMap
import com.asyncapi.kotlinasyncapi.model.server.ReferencableServerBindingsMap
import com.asyncapi.kotlinasyncapi.model.server.ReferencableServerVariablesMap
import com.asyncapi.kotlinasyncapi.model.server.ReferencableServersMap
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation

class AnnotationProvider(
    private val applicationPackage: Package? = null,
    private val classLoader: ClassLoader? = null,
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
        val packageName = applicationPackage?.name
        val annotatedClasses = packageName.let {
            scanner.scan(
                scanPackage = it,
                classLoader = classLoader,
                annotation = AsyncApiAnnotation::class
            )
        }

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
