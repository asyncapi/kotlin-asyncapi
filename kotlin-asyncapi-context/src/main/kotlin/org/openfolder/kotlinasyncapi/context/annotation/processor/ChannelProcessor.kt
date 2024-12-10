package org.openfolder.kotlinasyncapi.context.annotation.processor

import org.openfolder.kotlinasyncapi.annotation.channel.Channel
import org.openfolder.kotlinasyncapi.annotation.channel.Publish
import org.openfolder.kotlinasyncapi.annotation.channel.Subscribe
import org.openfolder.kotlinasyncapi.model.component.Components
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.functions
import kotlin.reflect.full.hasAnnotation

class ChannelProcessor : AnnotationProcessor<Channel, Any> {
    override fun process(annotation: Channel, context: Any): Components {
        return Components().apply {
            channels {
                when (context) {
                    is KClass<*> -> {
                        annotation.toChannel()
                            .apply {
                                subscribe = subscribe ?: context.findSubscribeOperation()?.toOperation()
                                publish = publish ?: context.findPublishOperation()?.toOperation()
                            }
                            .also {
                                put(context.java.simpleName, it)
                            }
                    }
                    is KFunction<*> -> {
                        annotation.toChannel()
                            .apply {
                                subscribe = subscribe ?: context.findAnnotation<Subscribe>()?.toOperation()
                                publish = publish ?: context.findAnnotation<Publish>()?.toOperation()
                            }
                            .also {
                                put(context.name, it)
                            }
                    }
                    else -> throw IllegalArgumentException("Unsupported context type: ${context::class}")
                }
            }
        }
    }

    private fun KClass<*>.findSubscribeOperation(): Subscribe? =
        functions.firstOrNull { it.hasAnnotation<Subscribe>() }?.findAnnotation()

    private fun KClass<*>.findPublishOperation(): Publish? =
        functions.firstOrNull { it.hasAnnotation<Publish>() }?.findAnnotation()
}