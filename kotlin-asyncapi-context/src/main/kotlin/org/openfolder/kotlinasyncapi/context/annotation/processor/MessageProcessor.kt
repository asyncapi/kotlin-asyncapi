package org.openfolder.kotlinasyncapi.context.annotation.processor

import org.openfolder.kotlinasyncapi.annotation.channel.Message
import org.openfolder.kotlinasyncapi.model.Reference
import org.openfolder.kotlinasyncapi.model.component.Components
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
