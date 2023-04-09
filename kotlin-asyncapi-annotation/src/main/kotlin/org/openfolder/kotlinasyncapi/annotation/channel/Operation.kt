package org.openfolder.kotlinasyncapi.annotation.channel

import org.openfolder.kotlinasyncapi.annotation.AsyncApiAnnotation
import org.openfolder.kotlinasyncapi.annotation.ExternalDocumentation
import org.openfolder.kotlinasyncapi.annotation.Tag

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.ANNOTATION_CLASS
)
@AsyncApiAnnotation
annotation class Subscribe(
    val default: Boolean = false,
    val operationId: String = "",
    val summary: String = "",
    val description: String = "",
    val security: Array<SecurityRequirement> = [],
    val tags: Array<Tag> = [],
    val externalDocs: ExternalDocumentation = ExternalDocumentation(default = true, url = ""),
    val bindings: OperationBindings = OperationBindings(default = true),
    val traits: Array<OperationTrait> = [],
    val message: Message = Message(default = true),
    val messages: Array<Message> = []
)

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.ANNOTATION_CLASS
)
@AsyncApiAnnotation
annotation class Publish(
    val default: Boolean = false,
    val operationId: String = "",
    val summary: String = "",
    val description: String = "",
    val security: Array<SecurityRequirement> = [],
    val tags: Array<Tag> = [],
    val externalDocs: ExternalDocumentation = ExternalDocumentation(default = true, url = ""),
    val bindings: OperationBindings = OperationBindings(default = true),
    val traits: Array<OperationTrait> = [],
    val message: Message = Message(default = true),
    val messages: Array<Message> = []
)
