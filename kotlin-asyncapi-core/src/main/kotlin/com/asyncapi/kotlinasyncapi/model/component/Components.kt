package com.asyncapi.kotlinasyncapi.model.component

import com.asyncapi.kotlinasyncapi.model.AsyncApiComponent
import com.asyncapi.kotlinasyncapi.model.ReferencableCorrelationIDsMap
import com.asyncapi.kotlinasyncapi.model.ReferencableSchemasMap
import com.asyncapi.kotlinasyncapi.model.channel.ReferencableChannelBindingsMap
import com.asyncapi.kotlinasyncapi.model.channel.ReferencableChannelsMap
import com.asyncapi.kotlinasyncapi.model.channel.ReferencableMessageBindingsMap
import com.asyncapi.kotlinasyncapi.model.channel.ReferencableMessageTraitsMap
import com.asyncapi.kotlinasyncapi.model.channel.ReferencableMessagesMap
import com.asyncapi.kotlinasyncapi.model.channel.ReferencableOperationBindingsMap
import com.asyncapi.kotlinasyncapi.model.channel.ReferencableOperationTraitsMap
import com.asyncapi.kotlinasyncapi.model.channel.ReferencableParametersMap
import com.asyncapi.kotlinasyncapi.model.server.ReferencableServerBindingsMap
import com.asyncapi.kotlinasyncapi.model.server.ReferencableServerVariablesMap
import com.asyncapi.kotlinasyncapi.model.server.ReferencableServersMap

@AsyncApiComponent
class Components {
    var schemas: ReferencableSchemasMap? = null
    var servers: ReferencableServersMap? = null
    var serverVariables: ReferencableServerVariablesMap? = null
    var channels: ReferencableChannelsMap? = null
    var messages: ReferencableMessagesMap? = null
    var securitySchemes: ReferencableSecuritySchemasMap? = null
    var parameters: ReferencableParametersMap? = null
    var correlationIds: ReferencableCorrelationIDsMap? = null
    var operationTraits: ReferencableOperationTraitsMap? = null
    var messageTraits: ReferencableMessageTraitsMap? = null
    var serverBindings: ReferencableServerBindingsMap? = null
    var channelBindings: ReferencableChannelBindingsMap? = null
    var operationBindings: ReferencableOperationBindingsMap? = null
    var messageBindings: ReferencableMessageBindingsMap? = null

    inline fun schemas(build: ReferencableSchemasMap.() -> Unit): ReferencableSchemasMap =
        ReferencableSchemasMap().apply(build).also { schemas = it }

    inline fun servers(build: ReferencableServersMap.() -> Unit): ReferencableServersMap =
        ReferencableServersMap().apply(build).also { servers = it }

    inline fun serverVariables(build: ReferencableServerVariablesMap.() -> Unit): ReferencableServerVariablesMap =
        ReferencableServerVariablesMap().apply(build).also { serverVariables = it }

    inline fun channels(build: ReferencableChannelsMap.() -> Unit): ReferencableChannelsMap =
        ReferencableChannelsMap().apply(build).also { channels = it }

    inline fun messages(build: ReferencableMessagesMap.() -> Unit): ReferencableMessagesMap =
        ReferencableMessagesMap().apply(build).also { messages = it }

    inline fun securitySchemes(build: ReferencableSecuritySchemasMap.() -> Unit): ReferencableSecuritySchemasMap =
        ReferencableSecuritySchemasMap().apply(build).also { securitySchemes = it }

    inline fun parameters(build: ReferencableParametersMap.() -> Unit): ReferencableParametersMap =
        ReferencableParametersMap().apply(build).also { parameters = it }

    inline fun correlationIds(build: ReferencableCorrelationIDsMap.() -> Unit): ReferencableCorrelationIDsMap =
        ReferencableCorrelationIDsMap().apply(build).also { correlationIds = it }

    inline fun operationTraits(build: ReferencableOperationTraitsMap.() -> Unit): ReferencableOperationTraitsMap =
        ReferencableOperationTraitsMap().apply(build).also { operationTraits = it }

    inline fun messageTraits(build: ReferencableMessageTraitsMap.() -> Unit): ReferencableMessageTraitsMap =
        ReferencableMessageTraitsMap().apply(build).also { messageTraits = it }

    inline fun serverBindings(build: ReferencableServerBindingsMap.() -> Unit): ReferencableServerBindingsMap =
        ReferencableServerBindingsMap().apply(build).also { serverBindings = it }

    inline fun channelBindings(build: ReferencableChannelBindingsMap.() -> Unit): ReferencableChannelBindingsMap =
        ReferencableChannelBindingsMap().apply(build).also { channelBindings = it }

    inline fun operationBindings(build: ReferencableOperationBindingsMap.() -> Unit): ReferencableOperationBindingsMap =
        ReferencableOperationBindingsMap().apply(build).also { operationBindings = it }

    inline fun messageBindings(build: ReferencableMessageBindingsMap.() -> Unit): ReferencableMessageBindingsMap =
        ReferencableMessageBindingsMap().apply(build).also { messageBindings = it }
}
