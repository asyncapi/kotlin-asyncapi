package com.asyncapi.kotlinasyncapi.context.annotation.processor

import com.asyncapi.kotlinasyncapi.annotation.Schema
import com.asyncapi.kotlinasyncapi.model.component.Components
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
