package org.openfolder.kotlinasyncapi.springweb.context

import org.openfolder.kotlinasyncapi.annotation.AsyncApiAnnotation
import org.openfolder.kotlinasyncapi.annotation.channel.Message
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

internal interface AnnotationProvider {

    val components: Components?
}

@Component
internal class DefaultAnnotationProvider(
    context: ApplicationContext,
    scanner: AnnotationScanner,
    messageProcessor: AnnotationProcessor<Message>
): AnnotationProvider {

    override val components: Components? by lazy {
        val scanPackage = context.getBeansWithAnnotation(EnableAsyncApi::class.java).values
            .firstOrNull()
            ?.let { it::class.java.packageName }
            ?.takeIf { it.isNotEmpty() }

        val annotatedClasses = scanner.scan(scanPackage = scanPackage!!, annotation = AsyncApiAnnotation::class)
        val annotationComponents = mutableListOf<Components>()

        for (clazz in annotatedClasses) {
            for (annotation in clazz.annotations) {
                if (annotation.annotationClass == Message::class) {
                    annotationComponents.add(messageProcessor.process(annotation as Message, clazz))
                }
            }
        }

        Components().merge(annotationComponents)
    }

    private fun Components.merge(components: List<Components>): Components {
        for (component in components) {
            component.schemas?.also {
                schemas = (schemas ?: ReferencableSchemasMap()).apply { putAll(it) }
            }
            component.servers?.also {
                servers = (servers ?: ReferencableServersMap()).apply { putAll(it) }
            }
            component.serverVariables?.also {
                serverVariables = (serverVariables ?: ReferencableServerVariablesMap()).apply { putAll(it) }
            }
            component.channels?.also {
                channels = (channels ?: ReferencableChannelsMap()).apply { putAll(it) }
            }
            component.messages?.also {
                messages = (messages ?: ReferencableMessagesMap()).apply { putAll(it) }
            }
            component.securitySchemes?.also {
                securitySchemes = (securitySchemes ?: ReferencableSecuritySchemasMap()).apply { putAll(it) }
            }
            component.parameters?.also {
                parameters = (parameters ?: ReferencableParametersMap()).apply { putAll(it) }
            }
            component.correlationIds?.also {
                correlationIds = (correlationIds ?: ReferencableCorrelationIDsMap()).apply { putAll(it) }
            }
            component.operationTraits?.also {
                operationTraits = (operationTraits ?: ReferencableOperationTraitsMap()).apply { putAll(it) }
            }
            component.messageTraits?.also {
                messageTraits = (messageTraits ?: ReferencableMessageTraitsMap()).apply { putAll(it) }
            }
            component.serverBindings?.also {
                serverBindings = (serverBindings ?: ReferencableServerBindingsMap()).apply { putAll(it) }
            }
            component.channelBindings?.also {
                channelBindings = (channelBindings ?: ReferencableChannelBindingsMap()).apply { putAll(it) }
            }
            component.operationBindings?.also {
                operationBindings = (operationBindings ?: ReferencableOperationBindingsMap()).apply { putAll(it) }
            }
            component.messageBindings?.also {
                messageBindings = (messageBindings ?: ReferencableMessageBindingsMap()).apply { putAll(it) }
            }
        }

        return this
    }
}
