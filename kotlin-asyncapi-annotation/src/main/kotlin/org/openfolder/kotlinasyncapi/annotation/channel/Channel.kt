package org.openfolder.kotlinasyncapi.annotation.channel

import org.openfolder.kotlinasyncapi.annotation.AsyncApiAnnotation

@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.ANNOTATION_CLASS
)
@AsyncApiAnnotation
annotation class Channel(
    val default: Boolean = false,
    val reference: String = "",
    val description: String = "",
    val servers: Array<String> = [],
    val subscribe: SubscribeOperation = SubscribeOperation(default = true),
    val publish: PublishOperation = PublishOperation(default = true),
    val parameters: Parameters = Parameters(default = true),
    val bindings: ChannelBindings = ChannelBindings(default = true)
)
