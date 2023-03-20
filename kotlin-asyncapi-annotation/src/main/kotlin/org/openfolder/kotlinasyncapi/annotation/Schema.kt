package org.openfolder.kotlinasyncapi.annotation

import kotlin.reflect.KClass

@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.ANNOTATION_CLASS
)
@Repeatable
@AsyncApiAnnotation
annotation class Schema(
    val default: Boolean = false,
    val implementation: KClass<*> = Void::class
)
