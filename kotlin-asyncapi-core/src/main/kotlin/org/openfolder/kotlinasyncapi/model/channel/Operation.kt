package org.openfolder.kotlinasyncapi.model.channel

import org.openfolder.kotlinasyncapi.model.AsyncApiComponent
import org.openfolder.kotlinasyncapi.model.ExternalDocumentation
import org.openfolder.kotlinasyncapi.model.Reference
import org.openfolder.kotlinasyncapi.model.TagsList

@AsyncApiComponent
class Operation {
    var operationId: String? = null
    var summary: String? = null
    var description: String? = null
    var tags: TagsList? = null
    var externalDocs: ExternalDocumentation? = null
    var bindings: Any? = null
    var traits: ReferencableOperationTraitsList? = null
    var message: Any? = null

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

    inline fun traits(build: ReferencableOperationTraitsList.() -> Unit): ReferencableOperationTraitsList =
        ReferencableOperationTraitsList().apply(build).also { traits = it }

    inline fun message(build: Message.() -> Unit): Message =
        Message().apply(build).also { message = it }

    inline fun messageRef(build: Reference.() -> Unit): Reference =
        Reference().apply(build).also { message = it }

    inline fun messages(build: OneOfReferencableMessages.() -> Unit): OneOfReferencableMessages =
        OneOfReferencableMessages().apply(build).also { message = it }
}
