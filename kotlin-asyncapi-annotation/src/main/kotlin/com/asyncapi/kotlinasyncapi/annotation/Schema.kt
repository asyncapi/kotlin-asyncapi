package com.asyncapi.kotlinasyncapi.annotation

import kotlin.reflect.KClass

@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.ANNOTATION_CLASS
)
@AsyncApiAnnotation
annotation class Schema(
    val value: KClass<*> = Void::class,
    val isDefault: kotlin.Boolean = false,
    val title: String = "",
    val description: String = "",
    val default: String = "",
    val readOnly: Boolean = Boolean(isDefault = true),
    val writeOnly: Boolean = Boolean(isDefault = true),
    val examples: Array<String> = [],
    val contentEncoding: String = "",
    val contentMediaType: String = "",
    val type: String = "",
    val enum: Array<String> = [],
    val const: String = "",
    val multipleOf: Int = Int(isDefault = true),
    val maximum: Int = Int(isDefault = true),
    val exclusiveMaximum: Int = Int(isDefault = true),
    val minimum: Int = Int(isDefault = true),
    val exclusiveMinimum: Int = Int(isDefault = true),
    val maxLength: Int = Int(isDefault = true),
    val minLength: Int = Int(isDefault = true),
    val pattern: String = "",
    val items: Array<Schema> = [],
    val additionalItems: Array<Schema> = [],
    val maxItems: Int = Int(isDefault = true),
    val minItems: Int = Int(isDefault = true),
    val uniqueItems: Boolean = Boolean(isDefault = true),
    val contains: Array<Schema> = [],
    val maxProperties: Int = Int(isDefault = true),
    val minProperties: Int = Int(isDefault = true),
    val required: Array<String> = [],
    val properties: Array<SchemaMapEntry> = [],
    val additionalProperties: Array<SchemaMapEntry> = [],
    val patternProperties: Array<SchemaMapEntry> = [],
    val dependencies: Array<SchemaMapEntry> = [],
    val propertyNames: Array<Schema> = [],
    val `if`: Array<Schema> = [],
    val then: Array<Schema> = [],
    val `else`: Array<Schema> = [],
    val allOf: Array<Schema> = [],
    val anyOf: Array<Schema> = [],
    val oneOf: Array<Schema> = [],
    val not: Array<Schema> = [],
    val format: String = "",
    val discriminator: String = "",
    val externalDocs: ExternalDocumentation = ExternalDocumentation(isDefault = true, url = ""),
    val deprecated: Boolean = Boolean(isDefault = true)
)

annotation class SchemaMapEntry(
    val isDefault: kotlin.Boolean = true,
    val key: String = "",
    val value: Schema = Schema(isDefault = true)
)
