package org.openfolder.kotlinasyncapi.springweb.context.annotation.processor

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator
import org.openfolder.kotlinasyncapi.annotation.channel.Message
import org.openfolder.kotlinasyncapi.model.component.Components
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
class MessageProcessor: AnnotationProcessor<Message> {
    override fun process(annotation: Message, clazz: KClass<*>?): Components {
        val objectMapper = ObjectMapper()
        val schemaGenerator = JsonSchemaGenerator(objectMapper)
        val jsonSchema = clazz?.let {
            schemaGenerator.generateSchema(it.java)
        }

        return Components().apply {
            messages {
                annotation.toMessage()
                    .apply { payload = payload ?: jsonSchema }
                    .let { put(clazz.toString(), it) }
            }
        }
    }
}
