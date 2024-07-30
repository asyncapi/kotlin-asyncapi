package org.openfolder.kotlinasyncapi.context.annotation.processor

import org.openfolder.kotlinasyncapi.annotation.Schema
import org.openfolder.kotlinasyncapi.model.component.Components
import kotlin.reflect.KClass

class SchemaProcessor : AnnotationProcessor<Schema, KClass<*>> {
    override fun process(annotation: Schema, context: KClass<*>): Components {
        val jsonSchema = MODEL_RESOLVER.readAll(context.java)

        return Components().apply {
            schemas {
                putAll(jsonSchema)
            }
        }
    }
}
