package org.openfolder.kotlinasyncapi.springweb.context.annotation.processor

import org.openfolder.kotlinasyncapi.model.component.Components

internal interface AnnotationProcessor<T, U> {
    fun process(annotation: T, context: U): Components
}
