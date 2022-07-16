package org.openfolder.kotlinasyncapi.model.channel

import org.openfolder.kotlinasyncapi.model.AsyncApiComponent
import org.openfolder.kotlinasyncapi.model.CorrelationID
import org.openfolder.kotlinasyncapi.model.ExternalDocumentation
import org.openfolder.kotlinasyncapi.model.Reference
import org.openfolder.kotlinasyncapi.model.Schema
import org.openfolder.kotlinasyncapi.model.TagsList

@AsyncApiComponent
class ReferencableMessagesList : ArrayList<Any>() {
    inline fun message(build: Message.() -> Unit): Message =
        Message().apply(build).also { add(it) }

    inline fun reference(build: Reference.() -> Unit): Reference =
        Reference().apply(build).also { add(it) }
}

@AsyncApiComponent
class ReferencableMessagesMap : LinkedHashMap<String, Any>() {
    inline fun message(key: String, build: Message.() -> Unit): Message =
        Message().apply(build).also { put(key, it) }

    inline fun reference(key: String, build: Reference.() -> Unit): Reference =
        Reference().apply(build).also { put(key, it) }
}

@AsyncApiComponent
class OneOfReferencableMessages {
    var oneOf: ReferencableMessagesList? = null

    inline fun oneOf(build: ReferencableMessagesList.() -> Unit): ReferencableMessagesList =
        ReferencableMessagesList().apply(build).also { oneOf = it }
}

@AsyncApiComponent
class Message {
    var messageId: String? = null
    var headers: Any? = null
    var payload: Any? = null
    var correlationId: Any? = null
    var schemaFormat: String? = null
    var contentType: String? = null
    var name: String? = null
    var title: String? = null
    var summary: String? = null
    var description: String? = null
    var tags: TagsList? = null
    var externalDocs: ExternalDocumentation? = null
    var bindings: ReferencableMessageBindingsMap? = null
    var examples: MessageExamplesList? = null
    var traits: MessageTraitsList? = null

    fun messageId(value: String): String =
        value.also { messageId = it }

    inline fun headers(build: Schema.() -> Unit): Schema =
        Schema().apply(build).also { headers = it }

    inline fun headersRef(build: Reference.() -> Unit): Reference =
        Reference().apply(build).also { headers = it }

    inline fun payload(build: Schema.() -> Unit): Schema =
        Schema().apply(build).also { payload = it }

    inline fun payloadRef(build: Reference.() -> Unit): Reference =
        Reference().apply(build).also { payload = it }

    inline fun correlationId(build: CorrelationID.() -> Unit): CorrelationID =
        CorrelationID().apply(build).also { correlationId = it }

    inline fun correlationIdRef(build: Reference.() -> Unit): Reference =
        Reference().apply(build).also { correlationId = it }

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

    inline fun bindings(build: ReferencableMessageBindingsMap.() -> Unit): ReferencableMessageBindingsMap =
        ReferencableMessageBindingsMap().apply(build).also { bindings = it }

    inline fun examples(build: MessageExamplesList.() -> Unit): MessageExamplesList =
        MessageExamplesList().apply(build).also { examples = it }

    inline fun traits(build: MessageTraitsList.() -> Unit): MessageTraitsList =
        MessageTraitsList().apply(build).also { traits = it }
}
