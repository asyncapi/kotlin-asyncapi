package org.openfolder.kotlinasyncapi.springweb.context.annotation.processor

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.openfolder.kotlinasyncapi.annotation.CorrelationID
import org.openfolder.kotlinasyncapi.annotation.ExternalDocumentation
import org.openfolder.kotlinasyncapi.annotation.Tag
import org.openfolder.kotlinasyncapi.annotation.channel.Message
import org.openfolder.kotlinasyncapi.annotation.channel.MessageBinding
import org.openfolder.kotlinasyncapi.annotation.channel.MessageBindings
import org.openfolder.kotlinasyncapi.annotation.channel.MessageExample
import org.openfolder.kotlinasyncapi.annotation.channel.MessageTrait
import org.openfolder.kotlinasyncapi.model.Reference
import org.openfolder.kotlinasyncapi.model.TagsList
import org.openfolder.kotlinasyncapi.model.channel.MessageExamplesList
import org.openfolder.kotlinasyncapi.model.channel.MessageTraitsList

internal fun Message.toMessage(): org.openfolder.kotlinasyncapi.model.channel.Message =
    org.openfolder.kotlinasyncapi.model.channel.Message().apply {
        messageId = this@toMessage.messageId.takeIf { it.isNotEmpty() }
        schemaFormat = this@toMessage.schemaFormat.takeIf { it.isNotEmpty() }
        contentType = this@toMessage.contentType.takeIf { it.isNotEmpty() }
        name = this@toMessage.name.takeIf { it.isNotEmpty() }
        title = this@toMessage.title.takeIf { it.isNotEmpty() }
        summary = this@toMessage.summary.takeIf { it.isNotEmpty() }
        description = this@toMessage.description.takeIf { it.isNotEmpty() }
        correlationId = this@toMessage.correlationId.takeUnless { it.default }?.toCorrelationID()
        tags = this@toMessage.tags.takeUnless { it.isEmpty() }?.toTagsList()
        externalDocs = this@toMessage.externalDocs.takeUnless { it.default }?.toExternalDocumentation()
        examples = this@toMessage.examples.takeUnless { it.isEmpty() }?.toMessageExamplesList()
        bindings = this@toMessage.bindings.takeUnless { it.default }?.toMessageBindings()
        traits = this@toMessage.traits.takeUnless { it.isEmpty() }?.toMessageTraitsList()
        headers = this@toMessage.headers.takeUnless { it.default }?.implementation?.let {
            Reference().apply { ref("#/components/schemas/${it.simpleName}") }
        }
        payload = this@toMessage.payload.takeUnless { it.default }?.implementation?.let {
            Reference().apply { ref("#/components/schemas/${it.simpleName}") }
        }
    }

internal fun Array<Tag>.toTagsList(): TagsList =
    TagsList().apply {
        addAll(this@toTagsList.map { it.toTag() })
    }

internal fun Array<MessageExample>.toMessageExamplesList(): MessageExamplesList =
    MessageExamplesList().apply {
        addAll(this@toMessageExamplesList.map { it.toMessageExample() })
    }

internal fun Array<MessageTrait>.toMessageTraitsList(): MessageTraitsList =
    MessageTraitsList().apply {
        addAll(this@toMessageTraitsList.map { it.toMessageTrait() })
    }

internal fun CorrelationID.toCorrelationID(): org.openfolder.kotlinasyncapi.model.CorrelationID =
    org.openfolder.kotlinasyncapi.model.CorrelationID().apply {
        location = this@toCorrelationID.location
        description = this@toCorrelationID.description.takeIf { it.isNotEmpty() }
    }

internal fun ExternalDocumentation.toExternalDocumentation(): org.openfolder.kotlinasyncapi.model.ExternalDocumentation =
    org.openfolder.kotlinasyncapi.model.ExternalDocumentation().apply {
        url = this@toExternalDocumentation.url
        description = this@toExternalDocumentation.description.takeIf { it.isNotEmpty() }
    }

internal fun Tag.toTag(): org.openfolder.kotlinasyncapi.model.Tag =
    org.openfolder.kotlinasyncapi.model.Tag().apply {
        name = this@toTag.name
        description = this@toTag.description.takeIf { it.isNotEmpty() }
        externalDocs = this@toTag.externalDocs.takeUnless { it.default }?.toExternalDocumentation()
    }

internal fun MessageExample.toMessageExample(): org.openfolder.kotlinasyncapi.model.channel.MessageExample =
    org.openfolder.kotlinasyncapi.model.channel.MessageExample().apply {
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

internal fun MessageBindings.toMessageBindings(): org.openfolder.kotlinasyncapi.model.channel.MessageBindings =
    org.openfolder.kotlinasyncapi.model.channel.MessageBindings().apply {
        http = this@toMessageBindings.http.takeUnless { it.default }?.toHTTP()
        kafka = this@toMessageBindings.kafka.takeUnless { it.default }?.toKafka()
        anypointmq = this@toMessageBindings.anypointmq.takeUnless { it.default }?.toAnypointMQ()
        amqp = this@toMessageBindings.amqp.takeUnless { it.default }?.toAMQP()
        mqtt = this@toMessageBindings.mqtt.takeUnless { it.default }?.toMQTT()
        ibmmq = this@toMessageBindings.ibmmq.takeUnless { it.default }?.toIBMMQ()
    }

internal fun MessageTrait.toMessageTrait(): org.openfolder.kotlinasyncapi.model.channel.MessageTrait =
    org.openfolder.kotlinasyncapi.model.channel.MessageTrait().apply {
        messageId = this@toMessageTrait.messageId.takeIf { it.isNotEmpty() }
        schemaFormat = this@toMessageTrait.schemaFormat.takeIf { it.isNotEmpty() }
        contentType = this@toMessageTrait.contentType.takeIf { it.isNotEmpty() }
        name = this@toMessageTrait.name.takeIf { it.isNotEmpty() }
        title = this@toMessageTrait.title.takeIf { it.isNotEmpty() }
        summary = this@toMessageTrait.summary.takeIf { it.isNotEmpty() }
        description = this@toMessageTrait.description.takeIf { it.isNotEmpty() }
        correlationId = this@toMessageTrait.correlationId.takeUnless { it.default }?.toCorrelationID()
        tags = this@toMessageTrait.tags.takeUnless { it.isEmpty() }?.toTagsList()
        externalDocs = this@toMessageTrait.externalDocs.takeUnless { it.default }?.toExternalDocumentation()
        examples = this@toMessageTrait.examples.takeUnless { it.isEmpty() }?.toMessageExamplesList()
        bindings = this@toMessageTrait.bindings.takeUnless { it.default }?.toMessageBindings()
        headers = this@toMessageTrait.headers.takeUnless { it.default }?.implementation?.let {
            Reference().apply { ref("#/components/schemas/${it.simpleName}") }
        }
    }

internal fun MessageBinding.HTTP.toHTTP(): org.openfolder.kotlinasyncapi.model.channel.MessageBinding.HTTP =
    org.openfolder.kotlinasyncapi.model.channel.MessageBinding.HTTP().apply {
        bindingVersion = this@toHTTP.bindingVersion.takeIf { it.isNotEmpty() }
        headers = this@toHTTP.headers.takeUnless { it.default }?.implementation?.let {
            Reference().apply { ref("#/components/schemas/${it.simpleName}") }
        }
    }

internal fun MessageBinding.Kafka.toKafka(): org.openfolder.kotlinasyncapi.model.channel.MessageBinding.Kafka =
    org.openfolder.kotlinasyncapi.model.channel.MessageBinding.Kafka().apply {
        bindingVersion = this@toKafka.bindingVersion.takeIf { it.isNotEmpty() }
        key = this@toKafka.key.takeUnless { it.default }?.implementation?.let {
            Reference().apply { ref("#/components/schemas/${it.simpleName}") }
        }
    }

internal fun MessageBinding.AnypointMQ.toAnypointMQ(): org.openfolder.kotlinasyncapi.model.channel.MessageBinding.AnypointMQ =
    org.openfolder.kotlinasyncapi.model.channel.MessageBinding.AnypointMQ().apply {
        bindingVersion = this@toAnypointMQ.bindingVersion.takeIf { it.isNotEmpty() }
        headers = this@toAnypointMQ.headers.takeUnless { it.default }?.implementation?.let {
            Reference().apply { ref("#/components/schemas/${it.simpleName}") }
        }
    }

internal fun MessageBinding.AMQP.toAMQP(): org.openfolder.kotlinasyncapi.model.channel.MessageBinding.AMQP =
    org.openfolder.kotlinasyncapi.model.channel.MessageBinding.AMQP().apply {
        bindingVersion = this@toAMQP.bindingVersion.takeIf { it.isNotEmpty() }
        messageType = this@toAMQP.messageType.takeIf { it.isNotEmpty() }
        contentEncoding = this@toAMQP.contentEncoding.takeIf { it.isNotEmpty() }
    }

internal fun MessageBinding.MQTT.toMQTT(): org.openfolder.kotlinasyncapi.model.channel.MessageBinding.MQTT =
    org.openfolder.kotlinasyncapi.model.channel.MessageBinding.MQTT().apply {
        bindingVersion = this@toMQTT.bindingVersion.takeIf { it.isNotEmpty() }
    }

internal fun MessageBinding.IBMMQ.toIBMMQ(): org.openfolder.kotlinasyncapi.model.channel.MessageBinding.IBMMQ =
    org.openfolder.kotlinasyncapi.model.channel.MessageBinding.IBMMQ().apply {
        bindingVersion = this@toIBMMQ.bindingVersion.takeIf { it.isNotEmpty() }
        type = this@toIBMMQ.type.takeIf { it.isNotEmpty() }
        description = this@toIBMMQ.description.takeIf { it.isNotEmpty() }
        expiry = this@toIBMMQ.expiry
        headers = this@toIBMMQ.headers.takeIf { it.isNotEmpty() }
    }
