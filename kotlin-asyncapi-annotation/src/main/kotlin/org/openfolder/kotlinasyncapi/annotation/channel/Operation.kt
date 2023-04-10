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
    val isDefault: Boolean = false,
    val operationId: String = "",
    val summary: String = "",
    val description: String = "",
    val security: Array<SecurityRequirement> = [],
    val tags: Array<Tag> = [],
    val externalDocs: ExternalDocumentation = ExternalDocumentation(isDefault = true, url = ""),
    val bindings: OperationBindings = OperationBindings(isDefault = true),
    val traits: Array<OperationTrait> = [],
    val message: Message = Message(isDefault = true),
    val messages: Array<Message> = []
)

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.ANNOTATION_CLASS
)
@AsyncApiAnnotation
annotation class Publish(
    val isDefault: Boolean = false,
    val operationId: String = "",
    val summary: String = "",
    val description: String = "",
    val security: Array<SecurityRequirement> = [],
    val tags: Array<Tag> = [],
    val externalDocs: ExternalDocumentation = ExternalDocumentation(isDefault = true, url = ""),
    val bindings: OperationBindings = OperationBindings(isDefault = true),
    val traits: Array<OperationTrait> = [],
    val message: Message = Message(isDefault = true),
    val messages: Array<Message> = []
)
