package org.openfolder.kotlinasyncapi.model.component

import org.openfolder.kotlinasyncapi.model.AsyncApiComponent
import org.openfolder.kotlinasyncapi.model.ReferencableCorrelationIDsMap
import org.openfolder.kotlinasyncapi.model.ReferencableSchemasMap
import org.openfolder.kotlinasyncapi.model.channel.ChannelsMap
import org.openfolder.kotlinasyncapi.model.channel.ReferencableChannelBindingsMap
import org.openfolder.kotlinasyncapi.model.channel.ReferencableMessageBindingsMap
import org.openfolder.kotlinasyncapi.model.channel.ReferencableMessageTraitsMap
import org.openfolder.kotlinasyncapi.model.channel.ReferencableMessagesMap
import org.openfolder.kotlinasyncapi.model.channel.ReferencableOperationBindingsMap
import org.openfolder.kotlinasyncapi.model.channel.ReferencableOperationTraitsMap
import org.openfolder.kotlinasyncapi.model.channel.ReferencableParametersMap
import org.openfolder.kotlinasyncapi.model.server.ReferencableServerBindingsMap
import org.openfolder.kotlinasyncapi.model.server.ReferencableServersMap

@AsyncApiComponent
class Components {
    var schemas: ReferencableSchemasMap? = null
    var servers: ReferencableServersMap? = null
    var channels: ChannelsMap? = null
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

    inline fun channels(build: ChannelsMap.() -> Unit): ChannelsMap =
        ChannelsMap().apply(build).also { channels = it }

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
