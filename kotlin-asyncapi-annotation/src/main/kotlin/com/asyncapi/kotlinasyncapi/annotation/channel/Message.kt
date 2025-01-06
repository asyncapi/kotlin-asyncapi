package com.asyncapi.kotlinasyncapi.annotation.channel

import com.asyncapi.kotlinasyncapi.annotation.AsyncApiAnnotation
import com.asyncapi.kotlinasyncapi.annotation.CorrelationID
import com.asyncapi.kotlinasyncapi.annotation.ExternalDocumentation
import com.asyncapi.kotlinasyncapi.annotation.Schema
import com.asyncapi.kotlinasyncapi.annotation.Tag
import kotlin.reflect.KClass

@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.ANNOTATION_CLASS
)
@AsyncApiAnnotation
annotation class Message(
    val value: KClass<*> = Void::class,
    val isDefault: Boolean = false,
    val messageId: String = "",
    val schemaFormat: String = "",
    val contentType: String = "",
    val name: String = "",
    val title: String = "",
    val summary: String = "",
    val description: String = "",
    val headers: Schema = Schema(isDefault = true),
    val payload: Schema = Schema(isDefault = true),
    val correlationId: CorrelationID = CorrelationID(isDefault = true, location = ""),
    val tags: Array<Tag> = [],
    val externalDocs: ExternalDocumentation = ExternalDocumentation(isDefault = true, url = ""),
    val examples: Array<MessageExample> = [],
    val bindings: MessageBindings = MessageBindings(isDefault = true),
    val traits: Array<MessageTrait> = []
)
