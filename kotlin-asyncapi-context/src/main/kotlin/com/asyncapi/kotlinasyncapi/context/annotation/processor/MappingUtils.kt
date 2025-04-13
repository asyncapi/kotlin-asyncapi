package com.asyncapi.kotlinasyncapi.context.annotation.processor

import com.asyncapi.kotlinasyncapi.annotation.CorrelationID
import com.asyncapi.kotlinasyncapi.annotation.ExternalDocumentation
import com.asyncapi.kotlinasyncapi.annotation.Schema
import com.asyncapi.kotlinasyncapi.annotation.SchemaMapEntry
import com.asyncapi.kotlinasyncapi.annotation.Tag
import com.asyncapi.kotlinasyncapi.annotation.channel.Channel
import com.asyncapi.kotlinasyncapi.annotation.channel.ChannelBinding
import com.asyncapi.kotlinasyncapi.annotation.channel.ChannelBindings
import com.asyncapi.kotlinasyncapi.annotation.channel.Message
import com.asyncapi.kotlinasyncapi.annotation.channel.MessageBinding
import com.asyncapi.kotlinasyncapi.annotation.channel.MessageBindings
import com.asyncapi.kotlinasyncapi.annotation.channel.MessageExample
import com.asyncapi.kotlinasyncapi.annotation.channel.MessageTrait
import com.asyncapi.kotlinasyncapi.annotation.channel.OperationBinding
import com.asyncapi.kotlinasyncapi.annotation.channel.OperationBindings
import com.asyncapi.kotlinasyncapi.annotation.channel.OperationTrait
import com.asyncapi.kotlinasyncapi.annotation.channel.Parameter
import com.asyncapi.kotlinasyncapi.annotation.channel.Publish
import com.asyncapi.kotlinasyncapi.annotation.channel.SecurityRequirement
import com.asyncapi.kotlinasyncapi.annotation.channel.Subscribe
import com.asyncapi.kotlinasyncapi.model.ReferencableSchemasList
import com.asyncapi.kotlinasyncapi.model.ReferencableSchemasMap
import com.asyncapi.kotlinasyncapi.model.Reference
import com.asyncapi.kotlinasyncapi.model.TagsList
import com.asyncapi.kotlinasyncapi.model.channel.MessageExamplesList
import com.asyncapi.kotlinasyncapi.model.channel.OneOfReferencableMessages
import com.asyncapi.kotlinasyncapi.model.channel.Operation
import com.asyncapi.kotlinasyncapi.model.channel.ReferencableMessageTraitsList
import com.asyncapi.kotlinasyncapi.model.channel.ReferencableMessagesList
import com.asyncapi.kotlinasyncapi.model.channel.ReferencableOperationTraitsList
import com.asyncapi.kotlinasyncapi.model.channel.ReferencableParametersMap
import com.asyncapi.kotlinasyncapi.model.server.SecurityRequirementsList
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import java.util.AbstractMap

internal fun Message.toMessage(): com.asyncapi.kotlinasyncapi.model.channel.Message =
    com.asyncapi.kotlinasyncapi.model.channel.Message().apply {
        messageId = this@toMessage.messageId.takeIf { it.isNotEmpty() }
        schemaFormat = this@toMessage.schemaFormat.takeIf { it.isNotEmpty() }
        contentType = this@toMessage.contentType.takeIf { it.isNotEmpty() }
        name = this@toMessage.name.takeIf { it.isNotEmpty() }
        title = this@toMessage.title.takeIf { it.isNotEmpty() }
        summary = this@toMessage.summary.takeIf { it.isNotEmpty() }
        description = this@toMessage.description.takeIf { it.isNotEmpty() }
        correlationId = this@toMessage.correlationId.takeUnless { it.isDefault }?.toCorrelationID()
        tags = this@toMessage.tags.takeIf { it.isNotEmpty() }?.toTagsList()
        externalDocs = this@toMessage.externalDocs.takeUnless { it.isDefault }?.toExternalDocumentation()
        examples = this@toMessage.examples.takeIf { it.isNotEmpty() }?.toMessageExamplesList()
        bindings = this@toMessage.bindings.takeUnless { it.isDefault }?.toMessageBindings()
        traits = this@toMessage.traits.takeIf { it.isNotEmpty() }?.toMessageTraitsList()
        headers = this@toMessage.headers.takeUnless { it.isDefault }?.toSchemaOrReference()
        payload = this@toMessage.payload.takeUnless { it.isDefault }?.toSchemaOrReference()
    }

internal fun Message.toMessageOrReference(): Any =
    value.takeUnless { it == Void::class }?.let { toReference() } ?: toMessage()

internal fun Schema.toSchema(): com.asyncapi.kotlinasyncapi.model.Schema =
    com.asyncapi.kotlinasyncapi.model.Schema().apply {
        val objectMapper = ObjectMapper()

        title = this@toSchema.title.takeIf { it.isNotEmpty() }
        description = this@toSchema.description.takeIf { it.isNotEmpty() }
        default = this@toSchema.default.takeIf { it.isNotEmpty() }?.let {
            objectMapper.readValue(it, object : TypeReference<Any>() {})
        }
        readOnly = this@toSchema.readOnly.takeUnless { it.isDefault }?.value
        writeOnly = this@toSchema.writeOnly.takeUnless { it.isDefault }?.value
        examples = this@toSchema.examples.takeIf { it.isNotEmpty() }?.map {
            objectMapper.readValue(it, object : TypeReference<Any>() {})
        }?.toList()
        contentEncoding = this@toSchema.contentEncoding.takeIf { it.isNotEmpty() }
        contentMediaType = this@toSchema.contentMediaType.takeIf { it.isNotEmpty() }
        type = this@toSchema.type.takeIf { it.isNotEmpty() }
        enum = this@toSchema.enum.takeIf { it.isNotEmpty() }?.map {
            objectMapper.readValue(it, object : TypeReference<Any>() {})
        }?.toList()
        const = this@toSchema.const.takeIf { it.isNotEmpty() }?.let {
            objectMapper.readValue(it, object : TypeReference<Any>() {})
        }
        multipleOf = this@toSchema.multipleOf.takeUnless { it.isDefault }?.value
        maximum = this@toSchema.maximum.takeUnless { it.isDefault }?.value
        exclusiveMaximum = this@toSchema.exclusiveMaximum.takeUnless { it.isDefault }?.value
        minimum = this@toSchema.minimum.takeUnless { it.isDefault }?.value
        exclusiveMinimum = this@toSchema.exclusiveMinimum.takeUnless { it.isDefault }?.value
        maxLength = this@toSchema.maxLength.takeUnless { it.isDefault }?.value
        minLength = this@toSchema.minLength.takeUnless { it.isDefault }?.value
        pattern = this@toSchema.pattern.takeIf { it.isNotEmpty() }
        items = this@toSchema.items.firstOrNull()?.toSchemaOrReference()
        additionalItems = this@toSchema.additionalItems.firstOrNull()?.toSchemaOrReference()
        maxItems = this@toSchema.maxItems.takeUnless { it.isDefault }?.value
        minItems = this@toSchema.minItems.takeUnless { it.isDefault }?.value
        uniqueItems = this@toSchema.uniqueItems.takeUnless { it.isDefault }?.value
        contains = this@toSchema.contains.firstOrNull()?.toSchemaOrReference()
        maxProperties = this@toSchema.maxProperties.takeUnless { it.isDefault }?.value
        minProperties = this@toSchema.minProperties.takeUnless { it.isDefault }?.value
        required = this@toSchema.required.takeIf { it.isNotEmpty() }?.toList()
        properties = this@toSchema.properties.takeIf { it.isNotEmpty() }?.toReferencableSchemasMap()
        additionalProperties = this@toSchema.additionalProperties.takeIf { it.isNotEmpty() }?.toReferencableSchemasMap()
        patternProperties = this@toSchema.patternProperties.takeIf { it.isNotEmpty() }?.toReferencableSchemasMap()
        dependencies = this@toSchema.dependencies.takeIf { it.isNotEmpty() }?.toReferencableSchemasMap()
        propertyNames = this@toSchema.propertyNames.firstOrNull()?.toSchemaOrReference()
        `if` = this@toSchema.`if`.firstOrNull()?.toSchemaOrReference()
        then = this@toSchema.then.firstOrNull()?.toSchemaOrReference()
        `else` = this@toSchema.`else`.firstOrNull()?.toSchemaOrReference()
        allOf = this@toSchema.allOf.takeIf { it.isNotEmpty() }?.toReferencableSchemasList()
        anyOf = this@toSchema.anyOf.takeIf { it.isNotEmpty() }?.toReferencableSchemasList()
        oneOf = this@toSchema.oneOf.takeIf { it.isNotEmpty() }?.toReferencableSchemasList()
        not = this@toSchema.not.firstOrNull()?.toSchemaOrReference()
        format = this@toSchema.format.takeIf { it.isNotEmpty() }
        discriminator = this@toSchema.discriminator.takeIf { it.isNotEmpty() }?.let {
            objectMapper.readValue(it, object : TypeReference<Any>() {})
        }
        deprecated = this@toSchema.deprecated.takeUnless { it.isDefault }?.value
    }

internal fun Schema.toSchemaOrReference(): Any =
    value.takeUnless { it == Void::class }?.let { toReference() } ?: toSchema()

internal fun Array<SchemaMapEntry>.toReferencableSchemasMap(): ReferencableSchemasMap =
    ReferencableSchemasMap().apply {
        putAll(this@toReferencableSchemasMap.map { it.key to it.value.toSchemaOrReference() })
    }

internal fun Array<Schema>.toReferencableSchemasList(): ReferencableSchemasList =
    ReferencableSchemasList().apply {
        addAll(this@toReferencableSchemasList.map { it.toSchemaOrReference() })
    }

internal fun Array<Tag>.toTagsList(): TagsList =
    TagsList().apply {
        addAll(this@toTagsList.map { it.toTag() })
    }

internal fun Array<MessageExample>.toMessageExamplesList(): MessageExamplesList =
    MessageExamplesList().apply {
        addAll(this@toMessageExamplesList.map { it.toMessageExample() })
    }

internal fun Array<MessageTrait>.toMessageTraitsList(): ReferencableMessageTraitsList =
    ReferencableMessageTraitsList().apply {
        addAll(this@toMessageTraitsList.map { it.toMessageTrait() })
    }

internal fun CorrelationID.toCorrelationID(): com.asyncapi.kotlinasyncapi.model.CorrelationID =
    com.asyncapi.kotlinasyncapi.model.CorrelationID().apply {
        location = this@toCorrelationID.location
        description = this@toCorrelationID.description.takeIf { it.isNotEmpty() }
    }

internal fun ExternalDocumentation.toExternalDocumentation(): com.asyncapi.kotlinasyncapi.model.ExternalDocumentation =
    com.asyncapi.kotlinasyncapi.model.ExternalDocumentation().apply {
        url = this@toExternalDocumentation.url
        description = this@toExternalDocumentation.description.takeIf { it.isNotEmpty() }
    }

internal fun Tag.toTag(): com.asyncapi.kotlinasyncapi.model.Tag =
    com.asyncapi.kotlinasyncapi.model.Tag().apply {
        name = this@toTag.name
        description = this@toTag.description.takeIf { it.isNotEmpty() }
        externalDocs = this@toTag.externalDocs.takeUnless { it.isDefault }?.toExternalDocumentation()
    }

internal fun MessageExample.toMessageExample(): com.asyncapi.kotlinasyncapi.model.channel.MessageExample =
    com.asyncapi.kotlinasyncapi.model.channel.MessageExample().apply {
        val objectMapper = ObjectMapper()

        name = this@toMessageExample.name.takeIf { it.isNotEmpty() }
        summary = this@toMessageExample.summary.takeIf { it.isNotEmpty() }
        payload = this@toMessageExample.payload.takeIf { it.isNotEmpty() }?.let {
            objectMapper.readValue(it, object : TypeReference<Any>() {})
        }
        headers = this@toMessageExample.headers.takeIf { it.isNotEmpty() }?.let {
            objectMapper.readValue(it, object : TypeReference<Map<String, Any>>() {})
        }
    }

internal fun MessageBindings.toMessageBindings(): com.asyncapi.kotlinasyncapi.model.channel.MessageBindings =
    com.asyncapi.kotlinasyncapi.model.channel.MessageBindings().apply {
        http = this@toMessageBindings.http.takeUnless { it.isDefault }?.toHTTP()
        kafka = this@toMessageBindings.kafka.takeUnless { it.isDefault }?.toKafka()
        anypointmq = this@toMessageBindings.anypointmq.takeUnless { it.isDefault }?.toAnypointMQ()
        amqp = this@toMessageBindings.amqp.takeUnless { it.isDefault }?.toAMQP()
        mqtt = this@toMessageBindings.mqtt.takeUnless { it.isDefault }?.toMQTT()
        ibmmq = this@toMessageBindings.ibmmq.takeUnless { it.isDefault }?.toIBMMQ()
    }

internal fun MessageTrait.toMessageTrait(): com.asyncapi.kotlinasyncapi.model.channel.MessageTrait =
    com.asyncapi.kotlinasyncapi.model.channel.MessageTrait().apply {
        messageId = this@toMessageTrait.messageId.takeIf { it.isNotEmpty() }
        schemaFormat = this@toMessageTrait.schemaFormat.takeIf { it.isNotEmpty() }
        contentType = this@toMessageTrait.contentType.takeIf { it.isNotEmpty() }
        name = this@toMessageTrait.name.takeIf { it.isNotEmpty() }
        title = this@toMessageTrait.title.takeIf { it.isNotEmpty() }
        summary = this@toMessageTrait.summary.takeIf { it.isNotEmpty() }
        description = this@toMessageTrait.description.takeIf { it.isNotEmpty() }
        correlationId = this@toMessageTrait.correlationId.takeUnless { it.isDefault }?.toCorrelationID()
        tags = this@toMessageTrait.tags.takeIf { it.isNotEmpty() }?.toTagsList()
        externalDocs = this@toMessageTrait.externalDocs.takeUnless { it.isDefault }?.toExternalDocumentation()
        examples = this@toMessageTrait.examples.takeIf { it.isNotEmpty() }?.toMessageExamplesList()
        bindings = this@toMessageTrait.bindings.takeUnless { it.isDefault }?.toMessageBindings()
        headers = this@toMessageTrait.headers.takeUnless { it.isDefault }?.toSchemaOrReference()
    }

internal fun MessageBinding.HTTP.toHTTP(): com.asyncapi.kotlinasyncapi.model.channel.MessageBinding.HTTP =
    com.asyncapi.kotlinasyncapi.model.channel.MessageBinding.HTTP().apply {
        bindingVersion = this@toHTTP.bindingVersion.takeIf { it.isNotEmpty() }
        headers = this@toHTTP.headers.takeUnless { it.isDefault }?.toSchemaOrReference()
    }

internal fun MessageBinding.Kafka.toKafka(): com.asyncapi.kotlinasyncapi.model.channel.MessageBinding.Kafka =
    com.asyncapi.kotlinasyncapi.model.channel.MessageBinding.Kafka().apply {
        bindingVersion = this@toKafka.bindingVersion.takeIf { it.isNotEmpty() }
        key = this@toKafka.key.takeUnless { it.isDefault }?.toSchemaOrReference()
    }

internal fun MessageBinding.AnypointMQ.toAnypointMQ(): com.asyncapi.kotlinasyncapi.model.channel.MessageBinding.AnypointMQ =
    com.asyncapi.kotlinasyncapi.model.channel.MessageBinding.AnypointMQ().apply {
        bindingVersion = this@toAnypointMQ.bindingVersion.takeIf { it.isNotEmpty() }
        headers = this@toAnypointMQ.headers.takeUnless { it.isDefault }?.toSchemaOrReference()
    }

internal fun MessageBinding.AMQP.toAMQP(): com.asyncapi.kotlinasyncapi.model.channel.MessageBinding.AMQP =
    com.asyncapi.kotlinasyncapi.model.channel.MessageBinding.AMQP().apply {
        bindingVersion = this@toAMQP.bindingVersion.takeIf { it.isNotEmpty() }
        messageType = this@toAMQP.messageType.takeIf { it.isNotEmpty() }
        contentEncoding = this@toAMQP.contentEncoding.takeIf { it.isNotEmpty() }
    }

internal fun MessageBinding.MQTT.toMQTT(): com.asyncapi.kotlinasyncapi.model.channel.MessageBinding.MQTT =
    com.asyncapi.kotlinasyncapi.model.channel.MessageBinding.MQTT().apply {
        bindingVersion = this@toMQTT.bindingVersion.takeIf { it.isNotEmpty() }
    }

internal fun MessageBinding.IBMMQ.toIBMMQ(): com.asyncapi.kotlinasyncapi.model.channel.MessageBinding.IBMMQ =
    com.asyncapi.kotlinasyncapi.model.channel.MessageBinding.IBMMQ().apply {
        bindingVersion = this@toIBMMQ.bindingVersion.takeIf { it.isNotEmpty() }
        type = this@toIBMMQ.type.takeIf { it.isNotEmpty() }
        description = this@toIBMMQ.description.takeIf { it.isNotEmpty() }
        expiry = this@toIBMMQ.expiry.takeUnless { it.isDefault }?.value
        headers = this@toIBMMQ.headers.takeIf { it.isNotEmpty() }
    }

internal fun Channel.toChannel(): com.asyncapi.kotlinasyncapi.model.channel.Channel =
    com.asyncapi.kotlinasyncapi.model.channel.Channel().apply {
        description = this@toChannel.description.takeIf { it.isNotEmpty() }
        servers = this@toChannel.servers.takeIf { it.isNotEmpty() }?.toList()
        subscribe = this@toChannel.subscribe.takeUnless { it.isDefault }?.toOperation()
        publish = this@toChannel.publish.takeUnless { it.isDefault }?.toOperation()
        parameters = this@toChannel.parameters.takeIf { it.isNotEmpty() }?.toReferencableParametersMap()
        bindings = this@toChannel.bindings.takeUnless { it.isDefault }?.toChannelBindings()
    }

internal fun Subscribe.toOperation(): Operation =
    Operation().apply {
        operationId = this@toOperation.operationId.takeIf { it.isNotEmpty() }
        summary = this@toOperation.summary.takeIf { it.isNotEmpty() }
        description = this@toOperation.description.takeIf { it.isNotEmpty() }
        security = this@toOperation.security.takeIf { it.isNotEmpty() }?.toSecurityRequirementsList()
        tags = this@toOperation.tags.takeIf { it.isNotEmpty() }?.toTagsList()
        externalDocs = this@toOperation.externalDocs.takeUnless { it.isDefault }?.toExternalDocumentation()
        bindings = this@toOperation.bindings.takeUnless { it.isDefault }?.toOperationBindings()
        traits = this@toOperation.traits.takeIf { it.isNotEmpty() }?.toReferencableOperationTraitsList()
        message = this@toOperation.message.takeUnless { it.isDefault }?.toMessageOrReference()
            ?: this@toOperation.messages.takeIf { it.isNotEmpty() }?.toOneOfReferencableMessages()
    }

internal fun Publish.toOperation(): Operation =
    Operation().apply {
        operationId = this@toOperation.operationId.takeIf { it.isNotEmpty() }
        summary = this@toOperation.summary.takeIf { it.isNotEmpty() }
        description = this@toOperation.description.takeIf { it.isNotEmpty() }
        security = this@toOperation.security.takeIf { it.isNotEmpty() }?.toSecurityRequirementsList()
        tags = this@toOperation.tags.takeIf { it.isNotEmpty() }?.toTagsList()
        externalDocs = this@toOperation.externalDocs.takeUnless { it.isDefault }?.toExternalDocumentation()
        bindings = this@toOperation.bindings.takeUnless { it.isDefault }?.toOperationBindings()
        traits = this@toOperation.traits.takeIf { it.isNotEmpty() }?.toReferencableOperationTraitsList()
        message = this@toOperation.message.takeUnless { it.isDefault }?.toMessageOrReference()
            ?: this@toOperation.messages.takeIf { it.isNotEmpty() }?.toOneOfReferencableMessages()
    }

internal fun Message.toReference(): Reference =
    Reference().apply {
        ref("#/components/messages/${value.simpleName}")
    }

internal fun Schema.toReference(): Reference =
    Reference().apply {
        ref("#/components/schemas/${value.simpleName}")
    }

internal fun Array<Message>.toOneOfReferencableMessages(): OneOfReferencableMessages =
    OneOfReferencableMessages().apply {
        oneOf = toReferencableMessagesList()
    }

internal fun Array<Message>.toReferencableMessagesList(): ReferencableMessagesList =
    ReferencableMessagesList().apply {
        addAll(this@toReferencableMessagesList.map { it.toMessageOrReference() })
    }

internal fun Array<OperationTrait>.toReferencableOperationTraitsList(): ReferencableOperationTraitsList =
    ReferencableOperationTraitsList().apply {
        addAll(this@toReferencableOperationTraitsList.map { it.toOperationTrait() })
    }

internal fun OperationTrait.toOperationTrait(): com.asyncapi.kotlinasyncapi.model.channel.OperationTrait =
    com.asyncapi.kotlinasyncapi.model.channel.OperationTrait().apply {
        operationId = this@toOperationTrait.operationId.takeIf { it.isNotEmpty() }
        summary = this@toOperationTrait.summary.takeIf { it.isNotEmpty() }
        description = this@toOperationTrait.description.takeIf { it.isNotEmpty() }
        tags = this@toOperationTrait.tags.takeIf { it.isNotEmpty() }?.toTagsList()
        externalDocs = this@toOperationTrait.externalDocs.takeUnless { it.isDefault }?.toExternalDocumentation()
        bindings = this@toOperationTrait.bindings.takeUnless { it.isDefault }?.toOperationBindings()
    }

internal fun OperationBindings.toOperationBindings(): com.asyncapi.kotlinasyncapi.model.channel.OperationBindings =
    com.asyncapi.kotlinasyncapi.model.channel.OperationBindings().apply {
        http = this@toOperationBindings.http.takeUnless { it.isDefault }?.toHTTP()
        kafka = this@toOperationBindings.kafka.takeUnless { it.isDefault }?.toKafka()
        amqp = this@toOperationBindings.amqp.takeUnless { it.isDefault }?.toAMQP()
        mqtt = this@toOperationBindings.mqtt.takeUnless { it.isDefault }?.toMQTT()
        nats = this@toOperationBindings.nats.takeUnless { it.isDefault }?.toNATS()
        solace = this@toOperationBindings.solace.takeUnless { it.isDefault }?.toSolace()
    }

internal fun OperationBinding.HTTP.toHTTP(): com.asyncapi.kotlinasyncapi.model.channel.OperationBinding.HTTP =
    com.asyncapi.kotlinasyncapi.model.channel.OperationBinding.HTTP().apply {
        method = this@toHTTP.method.takeIf { it.isNotEmpty() }
        query = this@toHTTP.query.takeUnless { it.isDefault }?.toSchemaOrReference()
        bindingVersion = this@toHTTP.bindingVersion.takeIf { it.isNotEmpty() }
    }

internal fun OperationBinding.Kafka.toKafka(): com.asyncapi.kotlinasyncapi.model.channel.OperationBinding.Kafka =
    com.asyncapi.kotlinasyncapi.model.channel.OperationBinding.Kafka().apply {
        groupId = this@toKafka.groupId.takeUnless { it.isDefault }?.toSchemaOrReference()
        clientId = this@toKafka.clientId.takeUnless { it.isDefault }?.toSchemaOrReference()
        bindingVersion = this@toKafka.bindingVersion.takeIf { it.isNotEmpty() }
    }

internal fun OperationBinding.AMQP.toAMQP(): com.asyncapi.kotlinasyncapi.model.channel.OperationBinding.AMQP =
    com.asyncapi.kotlinasyncapi.model.channel.OperationBinding.AMQP().apply {
        expiration = this@toAMQP.expiration.takeUnless { it.isDefault }?.value
        userId = this@toAMQP.userId.takeIf { it.isNotEmpty() }
        cc = this@toAMQP.cc.takeIf { it.isNotEmpty() }?.toList()
        priority = this@toAMQP.priority.takeUnless { it.isDefault }?.value
        deliveryMode = this@toAMQP.deliveryMode.takeUnless { it.isDefault }?.value
        mandatory = this@toAMQP.mandatory.takeUnless { it.isDefault }?.value
        bcc = this@toAMQP.bcc.takeIf { it.isNotEmpty() }?.toList()
        replyTo = this@toAMQP.replyTo.takeIf { it.isNotEmpty() }
        timestamp = this@toAMQP.timestamp.takeUnless { it.isDefault }?.value
        ack = this@toAMQP.ack.takeUnless { it.isDefault }?.value
        bindingVersion = this@toAMQP.bindingVersion.takeIf { it.isNotEmpty() }
    }

internal fun OperationBinding.MQTT.toMQTT(): com.asyncapi.kotlinasyncapi.model.channel.OperationBinding.MQTT =
    com.asyncapi.kotlinasyncapi.model.channel.OperationBinding.MQTT().apply {
        qos = this@toMQTT.qos.takeUnless { it.isDefault }?.value
        retain = this@toMQTT.retain.takeUnless { it.isDefault }?.value
        bindingVersion = this@toMQTT.bindingVersion.takeIf { it.isNotEmpty() }
    }

internal fun OperationBinding.NATS.toNATS(): com.asyncapi.kotlinasyncapi.model.channel.OperationBinding.NATS =
    com.asyncapi.kotlinasyncapi.model.channel.OperationBinding.NATS().apply {
        queue = this@toNATS.queue.takeIf { it.isNotEmpty() }
        bindingVersion = this@toNATS.bindingVersion.takeIf { it.isNotEmpty() }
    }

internal fun OperationBinding.Solace.toSolace(): com.asyncapi.kotlinasyncapi.model.channel.OperationBinding.Solace =
    com.asyncapi.kotlinasyncapi.model.channel.OperationBinding.Solace().apply {
        bindingVersion = this@toSolace.bindingVersion.takeIf { it.isNotEmpty() }
        destinations = this@toSolace.destinations.takeIf { it.isNotEmpty() }?.toSolaceDestinationsList()
    }

internal fun Array<OperationBinding.SolaceDestination>.toSolaceDestinationsList(): com.asyncapi.kotlinasyncapi.model.channel.OperationBinding.Solace.DestinationsList =
    com.asyncapi.kotlinasyncapi.model.channel.OperationBinding.Solace.DestinationsList().apply {
        addAll(this@toSolaceDestinationsList.map { it.toSolaceDestination() })
    }

internal fun OperationBinding.SolaceDestination.toSolaceDestination(): com.asyncapi.kotlinasyncapi.model.channel.OperationBinding.Solace.Destination =
    com.asyncapi.kotlinasyncapi.model.channel.OperationBinding.Solace.Destination().apply {
        destinationType = this@toSolaceDestination.destinationType.takeIf { it.isNotEmpty() }
        deliveryMode = this@toSolaceDestination.deliveryMode.takeIf { it.isNotEmpty() }
        queue = this@toSolaceDestination.queue.takeUnless { it.isDefault }?.toSolaceDestinationQueue()
        topic = this@toSolaceDestination.topic.takeUnless { it.isDefault }?.toSolaceDestinationTopic()
    }

internal fun OperationBinding.SolaceDestinationQueue.toSolaceDestinationQueue(): com.asyncapi.kotlinasyncapi.model.channel.OperationBinding.Solace.Queue =
    com.asyncapi.kotlinasyncapi.model.channel.OperationBinding.Solace.Queue().apply {
        name = this@toSolaceDestinationQueue.name.takeIf { it.isNotEmpty() }
        topicSubscriptions = this@toSolaceDestinationQueue.topicSubscriptions.takeIf { it.isNotEmpty() }?.toList()
        accessType = this@toSolaceDestinationQueue.accessType.takeIf { it.isNotEmpty() }
    }

internal fun OperationBinding.SolaceDestinationTopic.toSolaceDestinationTopic(): com.asyncapi.kotlinasyncapi.model.channel.OperationBinding.Solace.Topic =
    com.asyncapi.kotlinasyncapi.model.channel.OperationBinding.Solace.Topic().apply {
        topicSubscriptions = this@toSolaceDestinationTopic.topicSubscriptions.takeIf { it.isNotEmpty() }?.toList()
    }

internal fun Array<SecurityRequirement>.toSecurityRequirementsList(): SecurityRequirementsList =
    SecurityRequirementsList().apply {
        addAll(this@toSecurityRequirementsList.map { AbstractMap.SimpleEntry(it.key, it.values.toList()) })
    }

internal fun Array<Parameter>.toReferencableParametersMap(): ReferencableParametersMap =
    ReferencableParametersMap().apply {
        putAll(this@toReferencableParametersMap.map { it.value to it.toParameter() })
    }

internal fun Parameter.toParameter(): com.asyncapi.kotlinasyncapi.model.channel.Parameter =
    com.asyncapi.kotlinasyncapi.model.channel.Parameter().apply {
        description = this@toParameter.description.takeIf { it.isNotEmpty() }
        location = this@toParameter.location.takeIf { it.isNotEmpty() }
        schema = this@toParameter.schema.takeUnless { it.isDefault }?.toSchemaOrReference()
    }

internal fun ChannelBindings.toChannelBindings(): com.asyncapi.kotlinasyncapi.model.channel.ChannelBindings =
    com.asyncapi.kotlinasyncapi.model.channel.ChannelBindings().apply {
        ws = this@toChannelBindings.ws.takeUnless { it.isDefault }?.toWebSockets()
        anypointmq = this@toChannelBindings.anypointmq.takeUnless { it.isDefault }?.toAnypointMQ()
        amqp = this@toChannelBindings.amqp.takeUnless { it.isDefault }?.toAMQP()
        ibmmq = this@toChannelBindings.ibmmq.takeUnless { it.isDefault }?.toIBMMQ()
    }

internal fun ChannelBinding.WebSockets.toWebSockets(): com.asyncapi.kotlinasyncapi.model.channel.ChannelBinding.WebSockets =
    com.asyncapi.kotlinasyncapi.model.channel.ChannelBinding.WebSockets().apply {
        method = this@toWebSockets.method.takeIf { it.isNotEmpty() }
        query = this@toWebSockets.query.takeUnless { it.isDefault }?.toSchemaOrReference()
        headers = this@toWebSockets.headers.takeUnless { it.isDefault }?.toSchemaOrReference()
        bindingVersion = this@toWebSockets.bindingVersion.takeIf { it.isNotEmpty() }
    }

internal fun ChannelBinding.AnypointMQ.toAnypointMQ(): com.asyncapi.kotlinasyncapi.model.channel.ChannelBinding.AnypointMQ =
    com.asyncapi.kotlinasyncapi.model.channel.ChannelBinding.AnypointMQ().apply {
        destination = this@toAnypointMQ.destination.takeIf { it.isNotEmpty() }
        destinationType = this@toAnypointMQ.destinationType.takeIf { it.isNotEmpty() }
        bindingVersion = this@toAnypointMQ.bindingVersion.takeIf { it.isNotEmpty() }
    }

internal fun ChannelBinding.AMQP.toAMQP(): com.asyncapi.kotlinasyncapi.model.channel.ChannelBinding.AMQP =
    com.asyncapi.kotlinasyncapi.model.channel.ChannelBinding.AMQP().apply {
        `is` = this@toAMQP.`is`.takeIf { it.isNotEmpty() }
        exchange = this@toAMQP.exchange.takeUnless { it.isDefault }?.toAMQPExchange()
        queue = this@toAMQP.queue.takeUnless { it.isDefault }?.toAMQPQueue()
        bindingVersion = this@toAMQP.bindingVersion.takeIf { it.isNotEmpty() }
    }

internal fun ChannelBinding.AMQPExchange.toAMQPExchange(): com.asyncapi.kotlinasyncapi.model.channel.ChannelBinding.AMQP.Exchange =
    com.asyncapi.kotlinasyncapi.model.channel.ChannelBinding.AMQP.Exchange().apply {
        name = this@toAMQPExchange.name.takeIf { it.isNotEmpty() }
        type = this@toAMQPExchange.type.takeIf { it.isNotEmpty() }
        vhost = this@toAMQPExchange.vhost.takeIf { it.isNotEmpty() }
        durable = this@toAMQPExchange.durable.takeUnless { it.isDefault }?.value
        autoDelete = this@toAMQPExchange.autoDelete.takeUnless { it.isDefault }?.value
    }

internal fun ChannelBinding.AMQPQueue.toAMQPQueue(): com.asyncapi.kotlinasyncapi.model.channel.ChannelBinding.AMQP.Queue =
    com.asyncapi.kotlinasyncapi.model.channel.ChannelBinding.AMQP.Queue().apply {
        name = this@toAMQPQueue.name.takeIf { it.isNotEmpty() }
        type = this@toAMQPQueue.type.takeIf { it.isNotEmpty() }
        vhost = this@toAMQPQueue.vhost.takeIf { it.isNotEmpty() }
        durable = this@toAMQPQueue.durable.takeUnless { it.isDefault }?.value
        exclusive = this@toAMQPQueue.exclusive.takeUnless { it.isDefault }?.value
        autoDelete = this@toAMQPQueue.autoDelete.takeUnless { it.isDefault }?.value
    }

internal fun ChannelBinding.IBMMQ.toIBMMQ(): com.asyncapi.kotlinasyncapi.model.channel.ChannelBinding.IBMMQ =
    com.asyncapi.kotlinasyncapi.model.channel.ChannelBinding.IBMMQ().apply {
        destinationType = this@toIBMMQ.destinationType.takeIf { it.isNotEmpty() }
        bindingVersion = this@toIBMMQ.bindingVersion.takeIf { it.isNotEmpty() }
        maxMsgLength = this@toIBMMQ.maxMsgLength.takeUnless { it.isDefault }?.value
        queue = this@toIBMMQ.queue.takeUnless { it.isDefault }?.toIBMMQQueue()
        topic = this@toIBMMQ.topic.takeUnless { it.isDefault }?.toIBMMQTopic()
    }

internal fun ChannelBinding.IBMMQQueue.toIBMMQQueue(): com.asyncapi.kotlinasyncapi.model.channel.ChannelBinding.IBMMQ.Queue =
    com.asyncapi.kotlinasyncapi.model.channel.ChannelBinding.IBMMQ.Queue().apply {
        objectName = this@toIBMMQQueue.objectName
        isPartitioned = this@toIBMMQQueue.isPartitioned.takeUnless { it.isDefault }?.value
        exclusive = this@toIBMMQQueue.exclusive.takeUnless { it.isDefault }?.value
    }

internal fun ChannelBinding.IBMMQTopic.toIBMMQTopic(): com.asyncapi.kotlinasyncapi.model.channel.ChannelBinding.IBMMQ.Topic =
    com.asyncapi.kotlinasyncapi.model.channel.ChannelBinding.IBMMQ.Topic().apply {
        string = this@toIBMMQTopic.string.takeIf { it.isNotEmpty() }
        objectName = this@toIBMMQTopic.objectName.takeIf { it.isNotEmpty() }
        durablePermitted = this@toIBMMQTopic.durablePermitted.takeUnless { it.isDefault }?.value
        lastMsgRetained = this@toIBMMQTopic.lastMsgRetained.takeUnless { it.isDefault }?.value
    }
