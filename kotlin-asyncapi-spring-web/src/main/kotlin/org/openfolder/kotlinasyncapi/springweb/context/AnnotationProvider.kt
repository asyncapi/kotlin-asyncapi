package org.openfolder.kotlinasyncapi.springweb.context

import org.openfolder.kotlinasyncapi.annotation.AsyncApiAnnotation
import org.openfolder.kotlinasyncapi.annotation.channel.Message
import org.openfolder.kotlinasyncapi.model.component.Components
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

        Components().merge(*annotationComponents.toTypedArray())
    }

    private fun Components.merge(vararg components: Components): Components = this // TODO
}
