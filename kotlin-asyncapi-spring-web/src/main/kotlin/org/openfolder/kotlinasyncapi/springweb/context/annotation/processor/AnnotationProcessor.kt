package org.openfolder.kotlinasyncapi.springweb.context.annotation.processor

import org.openfolder.kotlinasyncapi.model.component.Components
import kotlin.reflect.KClass

internal interface AnnotationProcessor<in T> {
    fun process(annotation: T, clazz: KClass<*>): Components
}
