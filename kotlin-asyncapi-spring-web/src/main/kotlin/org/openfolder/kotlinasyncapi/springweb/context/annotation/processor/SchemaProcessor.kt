package org.openfolder.kotlinasyncapi.springweb.context.annotation.processor

import io.swagger.v3.core.converter.ModelConverters
import org.openfolder.kotlinasyncapi.annotation.Schema
import org.openfolder.kotlinasyncapi.model.component.Components
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
internal class SchemaProcessor : AnnotationProcessor<Schema, KClass<*>> {
    override fun process(annotation: Schema, context: KClass<*>): Components {
        val converters = ModelConverters()
        val jsonSchema = converters.readAll(context.java)

        return Components().apply {
            schemas {
                putAll(jsonSchema)
            }
        }
    }
}
