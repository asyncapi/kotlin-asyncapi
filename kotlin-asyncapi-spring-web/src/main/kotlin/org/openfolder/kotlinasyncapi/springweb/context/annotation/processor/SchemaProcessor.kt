package org.openfolder.kotlinasyncapi.springweb.context.annotation.processor

import org.openfolder.kotlinasyncapi.annotation.Schema
import org.openfolder.kotlinasyncapi.model.component.Components
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
internal class SchemaProcessor : AnnotationProcessor<Schema, KClass<*>> {
    override fun process(annotation: Schema, context: KClass<*>): Components {
        val jsonSchema = MODEL_RESOLVER.readAll(context.java)

        return Components().apply {
            schemas {
                putAll(jsonSchema)
            }
        }
    }
}
