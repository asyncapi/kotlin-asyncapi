package org.openfolder.kotlinasyncapi.context.annotation.processor

import org.openfolder.kotlinasyncapi.model.component.Components

interface AnnotationProcessor<T, U> {
    fun process(annotation: T, context: U): Components
}
