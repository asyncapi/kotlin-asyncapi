package com.asyncapi.kotlinasyncapi.annotation.channel

import com.asyncapi.kotlinasyncapi.annotation.AsyncApiAnnotation
import com.asyncapi.kotlinasyncapi.annotation.ExternalDocumentation
import com.asyncapi.kotlinasyncapi.annotation.Tag

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
