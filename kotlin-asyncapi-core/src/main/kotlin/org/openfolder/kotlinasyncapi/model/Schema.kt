package org.openfolder.kotlinasyncapi.model

@AsyncApiComponent
class ReferencableSchemasList : ArrayList<Any>() {
    inline fun schema(build: Schema.() -> Unit): Schema =
        Schema().apply(build).also { add(it) }

    inline fun reference(build: Reference.() -> Unit): Reference =
        Reference().apply(build).also { add(it) }
}

@AsyncApiComponent
class ReferencableSchemasMap : LinkedHashMap<String, Any>() {
    inline fun schema(key: String, build: Schema.() -> Unit): Schema =
        Schema().apply(build).also { put(key, it) }

    inline fun reference(key: String, build: Reference.() -> Unit): Reference =
        Reference().apply(build).also { put(key, it) }
}

@AsyncApiComponent
class Schema {
    var title: String? = null
    var description: String? = null
    var default: Any? = null
    var readOnly: Boolean? = null
    var writeOnly: Boolean? = null
    var examples: List<Any>? = null
    var contentEncoding: String? = null
    var contentMediaType: String? = null
    var type: Any? = null
    var enum: List<Any>? = null
    var const: Any? = null
    var multipleOf: Int? = null
    var maximum: Int? = null
    var exclusiveMaximum: Int? = null
    var minimum: Int? = null
    var exclusiveMinimum: Int? = null
    var maxLength: Int? = null
    var minLength: Int? = null
    var pattern: String? = null
    var items: Any? = null
    var additionalItems: Any? = null
    var maxItems: Int? = null
    var minItems: Int? = null
    var uniqueItems: Boolean? = null
    var contains: Any? = null
    var maxProperties: Int? = null
    var minProperties: Int? = null
    var required: List<String>? = null
    var properties: ReferencableSchemasMap? = null
    var patternProperties: ReferencableSchemasMap? = null
    var additionalProperties: Any? = null
    var dependencies: Any? = null
    var propertyNames: Any? = null
    var `if`: Any? = null
    var then: Any? = null
    var `else`: Any? = null
    var allOf: ReferencableSchemasList? = null
    var anyOf: ReferencableSchemasList? = null
    var oneOf: ReferencableSchemasList? = null
    var not: Any? = null
    var format: String? = null
    var discriminator: String? = null
    var externalDocs: ExternalDocumentation? = null
    var deprecated: Boolean? = null

    fun title(value: String): String =
        value.also { title = it }

    fun description(value: String): String =
        value.also { description = it }

    fun default(value: Any): Any =
        value.also { default = it }

    fun readOnly(value: Boolean): Boolean =
        value.also { readOnly = it }

    fun writeOnly(value: Boolean): Boolean =
        value.also { writeOnly = it }

    fun examples(vararg values: Any): List<Any> =
        listOf(*values).also { examples = it }

    fun contentEncoding(value: String): String =
        value.also { contentEncoding = it }

    fun contentMediaType(value: String): String =
        value.also { contentMediaType = it }

    fun type(value: String): String =
        value.also { type = it }

    fun type(vararg values: String): List<String> =
        listOf(*values).also { type = it }

    fun enum(vararg values: String): List<String> =
        listOf(*values).also { enum = it }

    fun const(value: Any): Any =
        value.also { const = it }

    fun multipleOf(value: Int): Int =
        value.also { multipleOf = it }

    fun maximum(value: Int): Int =
        value.also { maximum = it }

    fun exclusiveMaximum(value: Int): Int =
        value.also { exclusiveMaximum = it }

    fun minimum(value: Int): Int =
        value.also { minimum = it }

    fun exclusiveMinimum(value: Int): Int =
        value.also { exclusiveMinimum = it }

    fun maxLength(value: Int): Int =
        value.also { maxLength = it }

    fun minLength(value: Int): Int =
        value.also { minLength = it }

    fun pattern(value: String): String =
        value.also { pattern = it }

    inline fun item(build: Schema.() -> Unit): Schema =
        Schema().apply(build).also { items = it }

    inline fun itemRef(build: Reference.() -> Unit): Reference =
        Reference().apply(build).also { items = it }

    inline fun items(build: ReferencableSchemasList.() -> Unit): ReferencableSchemasList =
        ReferencableSchemasList().apply(build).also { items = it }

    inline fun additionalItems(build: Schema.() -> Unit): Schema =
        Schema().apply(build).also { additionalItems = it }

    inline fun additionalItemsRef(build: Reference.() -> Unit): Reference =
        Reference().apply(build).also { additionalItems = it }

    fun maxItems(value: Int): Int =
        value.also { maxItems = it }

    fun minItems(value: Int): Int =
        value.also { minItems = it }

    fun uniqueItems(value: Boolean): Boolean =
        value.also { uniqueItems = it }

    inline fun contains(build: Schema.() -> Unit): Schema =
        Schema().apply(build).also { contains = it }

    inline fun containsRef(build: Reference.() -> Unit): Reference =
        Reference().apply(build).also { contains = it }

    fun maxProperties(value: Int): Int =
        value.also { maxProperties = it }

    fun minProperties(value: Int): Int =
        value.also { minProperties = it }

    fun required(vararg values: String): List<String> =
        listOf(*values).also { required = it }

    inline fun properties(build: ReferencableSchemasMap.() -> Unit): ReferencableSchemasMap =
        ReferencableSchemasMap().apply(build).also { properties = it }

    inline fun patternProperties(build: ReferencableSchemasMap.() -> Unit): ReferencableSchemasMap =
        ReferencableSchemasMap().apply(build).also { patternProperties = it }

    inline fun additionalProperties(build: Schema.() -> Unit): Schema =
        Schema().apply(build).also { additionalProperties = it }

    inline fun additionalPropertiesRef(build: Reference.() -> Unit): Reference =
        Reference().apply(build).also { additionalProperties = it }

    fun dependencies(value: Any): Any =
        value.also { dependencies = it }

    inline fun propertyNames(build: Schema.() -> Unit): Schema =
        Schema().apply(build).also { propertyNames = it }

    inline fun propertyNamesRef(build: Reference.() -> Unit): Reference =
        Reference().apply(build).also { propertyNames = it }

    inline fun `if`(build: Schema.() -> Unit): Schema =
        Schema().apply(build).also { `if` = it }

    inline fun ifRef(build: Reference.() -> Unit): Reference =
        Reference().apply(build).also { `if` = it }

    inline fun then(build: Schema.() -> Unit): Schema =
        Schema().apply(build).also { then = it }

    inline fun thenRef(build: Reference.() -> Unit): Reference =
        Reference().apply(build).also { then = it }

    inline fun `else`(build: Schema.() -> Unit): Schema =
        Schema().apply(build).also { `else` = it }

    inline fun elseRef(build: Reference.() -> Unit): Reference =
        Reference().apply(build).also { `else` = it }

    inline fun allOf(build: ReferencableSchemasList.() -> Unit): ReferencableSchemasList =
        ReferencableSchemasList().apply(build).also { allOf = it }

    inline fun anyOf(build: ReferencableSchemasList.() -> Unit): ReferencableSchemasList =
        ReferencableSchemasList().apply(build).also { anyOf = it }

    inline fun oneOf(build: ReferencableSchemasList.() -> Unit): ReferencableSchemasList =
        ReferencableSchemasList().apply(build).also { oneOf = it }

    inline fun not(build: Schema.() -> Unit): Schema =
        Schema().apply(build).also { not = it }

    inline fun notRef(build: Reference.() -> Unit): Reference =
        Reference().apply(build).also { not = it }

    fun format(value: String): String =
        value.also { format = it }

    fun discriminator(value: String): String =
        value.also { discriminator = it }

    inline fun externalDocs(build: ExternalDocumentation.() -> Unit): ExternalDocumentation =
        ExternalDocumentation().apply(build).also { externalDocs = it }

    fun deprecated(value: Boolean): Boolean =
        value.also { deprecated = it }
}
