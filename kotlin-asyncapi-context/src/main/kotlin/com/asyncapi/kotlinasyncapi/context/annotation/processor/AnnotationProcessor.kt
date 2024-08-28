package com.asyncapi.kotlinasyncapi.context.annotation.processor

import com.asyncapi.kotlinasyncapi.model.component.Components

interface AnnotationProcessor<T, U> {
    fun process(annotation: T, context: U): Components
}
