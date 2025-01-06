package com.asyncapi.kotlinasyncapi.context.annotation.processor

import com.asyncapi.kotlinasyncapi.annotation.channel.Channel
import com.asyncapi.kotlinasyncapi.annotation.channel.Publish
import com.asyncapi.kotlinasyncapi.annotation.channel.Subscribe
import com.asyncapi.kotlinasyncapi.model.component.Components
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.functions
import kotlin.reflect.full.hasAnnotation

class ChannelProcessor : AnnotationProcessor<Channel, KClass<*>> {
    override fun process(annotation: Channel, context: KClass<*>): Components {
        return Components().apply {
            channels {
                annotation.toChannel()
                    .apply {
                        subscribe = subscribe ?: context.findSubscribeOperation()?.toOperation()
                        publish = publish ?: context.findPublishOperation()?.toOperation()
                    }
                    .also {
                        put(context.java.simpleName, it)
                    }
            }
        }
    }

    private fun KClass<*>.findSubscribeOperation(): Subscribe? =
        functions.firstOrNull { it.hasAnnotation<Subscribe>() }?.findAnnotation()

    private fun KClass<*>.findPublishOperation(): Publish? =
        functions.firstOrNull { it.hasAnnotation<Publish>() }?.findAnnotation()
}
