package org.openfolder.kotlinasyncapi.annotation

@Target(AnnotationTarget.CLASS)
@AsyncApiAnnotation
annotation class AsyncApiComponent(
    val value: String = "",
)