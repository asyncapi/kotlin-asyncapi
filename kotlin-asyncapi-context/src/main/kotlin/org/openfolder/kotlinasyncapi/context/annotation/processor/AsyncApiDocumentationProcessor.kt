package org.openfolder.kotlinasyncapi.context.annotation.processor

import org.openfolder.kotlinasyncapi.annotation.AsyncApiDocumentation
import org.openfolder.kotlinasyncapi.annotation.channel.Channel
import org.openfolder.kotlinasyncapi.annotation.channel.Publish
import org.openfolder.kotlinasyncapi.annotation.channel.Subscribe
import org.openfolder.kotlinasyncapi.model.component.Components
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.functions
import kotlin.reflect.full.hasAnnotation

class AsyncApiDocumentationProcessor : AnnotationProcessor<AsyncApiDocumentation, KClass<*>> {
    override fun process(annotation: AsyncApiDocumentation, context: KClass<*>): Components {
        return Components().apply {
            channels {
                context.functions.filter { it.hasAnnotation<Channel>() }.forEach { currentFunction ->
                    currentFunction.findAnnotation<Channel>()!!.toChannel()
                        .apply {
                            subscribe = subscribe ?: currentFunction.findAnnotation<Subscribe>()?.toOperation()
                            publish = publish ?: currentFunction.findAnnotation<Publish>()?.toOperation()
                        }
                        .also {
                            put(currentFunction.name, it)
                        }
                }
            }
        }
    }
}