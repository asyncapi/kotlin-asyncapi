package org.openfolder.kotlinasyncapi.annotation.channel

import org.openfolder.kotlinasyncapi.annotation.AsyncApiAnnotation

@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.ANNOTATION_CLASS
)
@AsyncApiAnnotation
annotation class Channel(
    val default: Boolean = false,
    val key: String = "",
    val description: String = "",
    val servers: Array<String> = [],
    val subscribe: Subscribe = Subscribe(default = true),
    val publish: Publish = Publish(default = true),
    val parameters: Array<Parameter> = [],
    val bindings: ChannelBindings = ChannelBindings(default = true)
)
