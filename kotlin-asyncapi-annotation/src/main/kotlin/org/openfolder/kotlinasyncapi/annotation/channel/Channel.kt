package org.openfolder.kotlinasyncapi.annotation.channel

import org.openfolder.kotlinasyncapi.annotation.AsyncApiAnnotation

@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.ANNOTATION_CLASS
)
@AsyncApiAnnotation
annotation class Channel(
    val value: String = "",
    val isDefault: Boolean = false,
    val description: String = "",
    val servers: Array<String> = [],
    val subscribe: Subscribe = Subscribe(isDefault = true),
    val publish: Publish = Publish(isDefault = true),
    val parameters: Array<Parameter> = [],
    val bindings: ChannelBindings = ChannelBindings(isDefault = true)
)
