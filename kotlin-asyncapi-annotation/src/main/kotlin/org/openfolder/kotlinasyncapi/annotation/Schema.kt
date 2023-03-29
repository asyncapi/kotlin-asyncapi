package org.openfolder.kotlinasyncapi.annotation

import kotlin.reflect.KClass

@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.ANNOTATION_CLASS
)
@AsyncApiAnnotation
annotation class Schema(
    val value: KClass<*> = Void::class,
    val default: Boolean = false
)
