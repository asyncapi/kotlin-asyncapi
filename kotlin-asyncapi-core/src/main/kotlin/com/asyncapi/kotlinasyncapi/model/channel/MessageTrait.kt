package com.asyncapi.kotlinasyncapi.model.channel

import com.asyncapi.kotlinasyncapi.model.AsyncApiComponent
import com.asyncapi.kotlinasyncapi.model.CorrelationID
import com.asyncapi.kotlinasyncapi.model.ExternalDocumentation
import com.asyncapi.kotlinasyncapi.model.Reference
import com.asyncapi.kotlinasyncapi.model.Schema
import com.asyncapi.kotlinasyncapi.model.TagsList

@AsyncApiComponent
class MessageTraitsList : ArrayList<MessageTrait>() {
    inline fun trait(build: MessageTrait.() -> Unit): MessageTrait =
        MessageTrait().apply(build).also { add(it) }
}

@AsyncApiComponent
class ReferencableMessageTraitsMap : LinkedHashMap<String, Any>() {
    inline fun trait(key: String, build: MessageTrait.() -> Unit): MessageTrait =
        MessageTrait().apply(build).also { put(key, it) }

    inline fun reference(key: String, build: Reference.() -> Unit): Reference =
        Reference().apply(build).also { put(key, it) }
}

@AsyncApiComponent
class MessageTrait {
    var headers: Any? = null
    var correlationId: Any? = null
    var messageId: String? = null
    var schemaFormat: String? = null
    var contentType: String? = null
    var name: String? = null
    var title: String? = null
    var summary: String? = null
    var description: String? = null
    var tags: TagsList? = null
    var externalDocs: ExternalDocumentation? = null
    var bindings: Any? = null
    var examples: MessageExamplesList? = null

    inline fun headers(build: Schema.() -> Unit): Schema =
        Schema().apply(build).also { headers = it }

    inline fun headersRef(build: Reference.() -> Unit): Reference =
        Reference().apply(build).also { headers = it }

    inline fun correlationId(build: CorrelationID.() -> Unit): CorrelationID =
        CorrelationID().apply(build).also { correlationId = it }

    inline fun correlationIdRef(build: Reference.() -> Unit): Reference =
        Reference().apply(build).also { correlationId = it }

    fun messageId(value: String): String =
        value.also { messageId = it }

    fun schemaFormat(value: String): String =
        value.also { schemaFormat = it }

    fun contentType(value: String): String =
        value.also { contentType = it }

    fun name(value: String): String =
        value.also { name = it }

    fun title(value: String): String =
        value.also { title = it }

    fun summary(value: String): String =
        value.also { summary = it }

    fun description(value: String): String =
        value.also { description = it }

    inline fun tags(build: TagsList.() -> Unit): TagsList =
        TagsList().apply(build).also { tags = it }

    inline fun externalDocs(build: ExternalDocumentation.() -> Unit): ExternalDocumentation =
        ExternalDocumentation().apply(build).also { externalDocs = it }

    inline fun bindings(build: MessageBindings.() -> Unit): MessageBindings =
        MessageBindings().apply(build).also { bindings = it }

    inline fun bindingsRef(build: Reference.() -> Unit): Reference =
        Reference().apply(build).also { bindings = it }

    inline fun examples(build: MessageExamplesList.() -> Unit): MessageExamplesList =
        MessageExamplesList().apply(build).also { examples = it }
}
