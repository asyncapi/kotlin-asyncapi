package com.asyncapi.kotlinasyncapi.model.channel

import com.asyncapi.kotlinasyncapi.model.AsyncApiComponent
import com.asyncapi.kotlinasyncapi.model.ExternalDocumentation
import com.asyncapi.kotlinasyncapi.model.Reference
import com.asyncapi.kotlinasyncapi.model.TagsList

@AsyncApiComponent
class ReferencableOperationTraitsList : ArrayList<Any>() {
    inline fun trait(build: OperationTrait.() -> Unit): OperationTrait =
        OperationTrait().apply(build).also { add(it) }

    inline fun reference(build: Reference.() -> Unit): Reference =
        Reference().apply(build).also { add(it) }
}

@AsyncApiComponent
class ReferencableOperationTraitsMap : LinkedHashMap<String, Any>() {
    inline fun trait(key: String, build: OperationTrait.() -> Unit): OperationTrait =
        OperationTrait().apply(build).also { put(key, it) }

    inline fun reference(key: String, build: Reference.() -> Unit): Reference =
        Reference().apply(build).also { put(key, it) }
}

@AsyncApiComponent
class OperationTrait {
    var operationId: String? = null
    var summary: String? = null
    var description: String? = null
    var tags: TagsList? = null
    var externalDocs: ExternalDocumentation? = null
    var bindings: Any? = null

    fun operationId(value: String): String =
        value.also { operationId = it }

    fun summary(value: String): String =
        value.also { summary = it }

    fun description(value: String): String =
        value.also { description = it }

    inline fun tags(build: TagsList.() -> Unit): TagsList =
        TagsList().apply(build).also { tags = it }

    inline fun externalDocs(build: ExternalDocumentation.() -> Unit): ExternalDocumentation =
        ExternalDocumentation().apply(build).also { externalDocs = it }

    inline fun bindings(build: OperationBindings.() -> Unit): OperationBindings =
        OperationBindings().apply(build).also { bindings = it }

    inline fun bindingsRef(build: Reference.() -> Unit): Reference =
        Reference().apply(build).also { bindings = it }
}
