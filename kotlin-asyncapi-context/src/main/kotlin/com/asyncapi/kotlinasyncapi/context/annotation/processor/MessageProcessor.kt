package com.asyncapi.kotlinasyncapi.context.annotation.processor

import com.asyncapi.kotlinasyncapi.annotation.channel.Message
import com.asyncapi.kotlinasyncapi.model.Reference
import com.asyncapi.kotlinasyncapi.model.component.Components
import kotlin.reflect.KClass

class MessageProcessor : AnnotationProcessor<Message, KClass<*>> {
    override fun process(annotation: Message, context: KClass<*>): Components {
        val jsonSchema = MODEL_RESOLVER.readAll(context.java)

        return Components().apply {
            messages {
                annotation.toMessage()
                    .apply {
                        payload = payload ?: Reference().apply {
                            ref("#/components/schemas/${context.simpleName}")
                        }
                        schemaFormat = "application/schema+json;version=draft-07"
                    }
                    .also {
                        put(context.java.simpleName, it)
                    }
            }
            schemas {
                putAll(jsonSchema)
            }
        }
    }
}
