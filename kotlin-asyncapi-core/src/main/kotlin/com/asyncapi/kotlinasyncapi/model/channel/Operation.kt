package com.asyncapi.kotlinasyncapi.model.channel

import com.asyncapi.kotlinasyncapi.model.AsyncApiComponent
import com.asyncapi.kotlinasyncapi.model.ExternalDocumentation
import com.asyncapi.kotlinasyncapi.model.Reference
import com.asyncapi.kotlinasyncapi.model.TagsList
import com.asyncapi.kotlinasyncapi.model.server.SecurityRequirementsList

@AsyncApiComponent
class Operation {
    var operationId: String? = null
    var summary: String? = null
    var description: String? = null
    var security: SecurityRequirementsList? = null
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

    inline fun security(build: SecurityRequirementsList.() -> Unit): SecurityRequirementsList =
        SecurityRequirementsList().apply(build).also { security = it }

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
