package org.openfolder.kotlinasyncapi.annotation

import kotlin.reflect.KClass

annotation class Schema(
    val default: Boolean = false,
    val reference: String = "",
    val title: String = "",
    val description: String = "",
    val implementation: KClass<*> = Void::class,
    val readOnly: Boolean = false,
    val writeOnly: Boolean = false,
    val examples: Array<String> = [],
    val type: String = "",
    val multipleOf: Int = 0,
    val maximum: Int = 0,
    val exclusiveMaximum: Int = 0,
    val minimum: Int = 0,
    val exclusiveMinimum: Int = 0,
    val maxLength: Int = 0,
    val minLength: Int = 0,
    val pattern: String = "",
    val maxProperties: Int = 0,
    val minProperties: Int = 0,
    val required: Array<String> = [],
    val additionalProperties: String = "",
    val allOf: Array<KClass<*>> = [],
    val anyOf: Array<KClass<*>> = [],
    val oneOf: Array<KClass<*>> = [],
    val not: KClass<*> = Void::class,
    val format: String = "",
    val discriminator: String = "",
    val externalDocs: ExternalDocumentation = ExternalDocumentation(default = true, url = ""),
    val deprecated: Boolean = false
)
