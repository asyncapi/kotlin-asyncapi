package com.asyncapi.kotlinasyncapi.context.annotation.processor

import com.asyncapi.kotlinasyncapi.annotation.AsyncApiComponent
import com.asyncapi.kotlinasyncapi.annotation.channel.Channel
import com.asyncapi.kotlinasyncapi.annotation.channel.Publish
import com.asyncapi.kotlinasyncapi.annotation.channel.Subscribe
import com.asyncapi.kotlinasyncapi.model.component.Components
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