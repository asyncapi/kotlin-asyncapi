package org.openfolder.kotlinasyncapi.context.annotation.processor

import org.openfolder.kotlinasyncapi.annotation.AsyncApiComponent
import org.openfolder.kotlinasyncapi.annotation.channel.Channel
import org.openfolder.kotlinasyncapi.annotation.channel.Publish
import org.openfolder.kotlinasyncapi.annotation.channel.Subscribe
import org.openfolder.kotlinasyncapi.model.component.Components
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.functions
import kotlin.reflect.full.hasAnnotation

class AsyncApiComponentProcessor : AnnotationProcessor<AsyncApiComponent, KClass<*>> {
    override fun process(annotation: AsyncApiComponent, context: KClass<*>): Components {
        return Components().apply {
            channels {
                context.functions.filter { it.hasAnnotation<Channel>() }.forEach { currentFunction ->
                    var currentAnnotation = currentFunction.findAnnotation<Channel>()!!
                    currentAnnotation.toChannel()
                        .apply {
                            subscribe = subscribe ?: currentFunction.findAnnotation<Subscribe>()?.toOperation()
                            publish = publish ?: currentFunction.findAnnotation<Publish>()?.toOperation()
                        }
                        .also {
                            put(currentAnnotation.value, it)
                        }
                }
            }
        }
    }
}