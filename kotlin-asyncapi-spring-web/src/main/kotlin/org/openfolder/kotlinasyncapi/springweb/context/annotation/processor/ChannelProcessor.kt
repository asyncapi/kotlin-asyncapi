package org.openfolder.kotlinasyncapi.springweb.context.annotation.processor

import org.openfolder.kotlinasyncapi.annotation.channel.Channel
import org.openfolder.kotlinasyncapi.annotation.channel.Publish
import org.openfolder.kotlinasyncapi.annotation.channel.Subscribe
import org.openfolder.kotlinasyncapi.model.component.Components
import org.springframework.stereotype.Component
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.functions
import kotlin.reflect.full.hasAnnotation

@Component
internal class ChannelProcessor : AnnotationProcessor<Channel, KClass<*>> {
    override fun process(annotation: Channel, context: KClass<*>): Components {
        return Components().apply {
            channels {
                annotation.toChannel()
                    .apply {
                        subscribe = subscribe ?: context.findSubscribeOperation()?.toOperation()
                        publish = publish ?: context.findPublishOperation()?.toOperation()
                    }
                    .also { channel ->
                        put(annotation.key.takeIf { it.isNotEmpty() } ?: context.java.simpleName, channel)
                    }
            }
        }
    }

    private fun KClass<*>.findSubscribeOperation(): Subscribe? =
        functions.firstOrNull { it.hasAnnotation<Subscribe>() }?.findAnnotation()

    private fun KClass<*>.findPublishOperation(): Publish? =
        functions.firstOrNull { it.hasAnnotation<Publish>() }?.findAnnotation()
}
