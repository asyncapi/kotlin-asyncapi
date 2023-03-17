package org.openfolder.kotlinasyncapi.springweb.context.annotation.processor

import io.swagger.v3.core.converter.ModelConverters
import org.openfolder.kotlinasyncapi.annotation.channel.Message
import org.openfolder.kotlinasyncapi.model.Reference
import org.openfolder.kotlinasyncapi.model.component.Components
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
internal class MessageProcessor: AnnotationProcessor<Message> {
    override fun process(annotation: Message, clazz: KClass<*>): Components {
        val converters = ModelConverters()
        val jsonSchema = converters.readAll(clazz.java)

        return Components().apply {
            messages {
                annotation.toMessage()
                    .apply {
                        payload = payload ?: Reference().apply {
                            ref("#/components/schemas/${clazz.simpleName}")
                        }
                        schemaFormat = "application/schema+json;version=draft-07"
                    }
                    .also {
                        put(clazz.java.simpleName, it)
                    }
            }
            schemas {
                putAll(jsonSchema)
            }
        }
    }
}
