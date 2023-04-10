package org.openfolder.kotlinasyncapi.annotation.channel

import org.openfolder.kotlinasyncapi.annotation.Boolean
import org.openfolder.kotlinasyncapi.annotation.Int
import org.openfolder.kotlinasyncapi.annotation.Schema

annotation class OperationBindings(
    val isDefault: kotlin.Boolean = false,
    val http: OperationBinding.HTTP = OperationBinding.HTTP(isDefault = true, type = ""),
    val ws: OperationBinding.WebSockets = OperationBinding.WebSockets(isDefault = true),
    val kafka: OperationBinding.Kafka = OperationBinding.Kafka(isDefault = true),
    val anypointmq: OperationBinding.AnypointMQ = OperationBinding.AnypointMQ(isDefault = true),
    val amqp: OperationBinding.AMQP = OperationBinding.AMQP(isDefault = true),
    val amqp1: OperationBinding.AMQP1 = OperationBinding.AMQP1(isDefault = true),
    val mqtt: OperationBinding.MQTT = OperationBinding.MQTT(isDefault = true),
    val mqtt5: OperationBinding.MQTT5 = OperationBinding.MQTT5(isDefault = true),
    val nats: OperationBinding.NATS = OperationBinding.NATS(isDefault = true),
    val jms: OperationBinding.JMS = OperationBinding.JMS(isDefault = true),
    val sns: OperationBinding.SNS = OperationBinding.SNS(isDefault = true),
    val solace: OperationBinding.Solace = OperationBinding.Solace(isDefault = true),
    val sqs: OperationBinding.SQS = OperationBinding.SQS(isDefault = true),
    val stomp: OperationBinding.STOMP = OperationBinding.STOMP(isDefault = true),
    val redis: OperationBinding.Redis = OperationBinding.Redis(isDefault = true),
    val mercure: OperationBinding.Mercure = OperationBinding.Mercure(isDefault = true)
)

sealed interface OperationBinding {

    annotation class AMQP(
        val isDefault: kotlin.Boolean = false,
        val expiration: Int = Int(isDefault = true),
        val userId: String = "",
        val cc: Array<String> = [],
        val bcc: Array<String> = [],
        val replyTo: String = "",
        val timestamp: Boolean = Boolean(isDefault = true),
        val ack: Boolean = Boolean(isDefault = true),
        val priority: Int = Int(isDefault = true),
        val deliveryMode: Int = Int(isDefault = true),
        val mandatory: Boolean = Boolean(isDefault = true),
        val bindingVersion: String = ""
    )

    annotation class HTTP(
        val isDefault: kotlin.Boolean = false,
        val type: String,
        val method: String = "",
        val query: Schema = Schema(isDefault = true),
        val bindingVersion: String = ""
    )

    annotation class Kafka(
        val isDefault: kotlin.Boolean = false,
        val bindingVersion: String = "",
        val groupId: Schema = Schema(isDefault = true),
        val clientId: Schema = Schema(isDefault = true)
    )

    annotation class MQTT(
        val isDefault: kotlin.Boolean = false,
        val qos: Int = Int(isDefault = true),
        val retain: Boolean = Boolean(isDefault = true),
        val bindingVersion: String = ""
    )

    annotation class NATS(
        val isDefault: kotlin.Boolean = false,
        val queue: String = "",
        val bindingVersion: String = ""
    )

    annotation class Solace(
        val isDefault: kotlin.Boolean = false,
        val bindingVersion: String = "",
        val destinations: Array<SolaceDestination> = []
    )

    annotation class SolaceDestination(
        val isDefault: kotlin.Boolean = false,
        val destinationType: String = "",
        val deliveryMode: String = "",
        val queue: SolaceDestinationQueue = SolaceDestinationQueue(isDefault = true),
        val topic: SolaceDestinationTopic = SolaceDestinationTopic(isDefault = true)
    )

    annotation class SolaceDestinationQueue(
        val isDefault: kotlin.Boolean = false,
        val name: String = "",
        val accessType: String = "",
        val topicSubscriptions: Array<String> = []
    )

    annotation class SolaceDestinationTopic(
        val isDefault: kotlin.Boolean = false,
        val topicSubscriptions: Array<String> = []
    )

    annotation class WebSockets(val isDefault: kotlin.Boolean = false)

    annotation class AnypointMQ(val isDefault: kotlin.Boolean = false)

    annotation class AMQP1(val isDefault: kotlin.Boolean = false)

    annotation class MQTT5(val isDefault: kotlin.Boolean = false)

    annotation class JMS(val isDefault: kotlin.Boolean = false)

    annotation class SNS(val isDefault: kotlin.Boolean = false)

    annotation class SQS(val isDefault: kotlin.Boolean = false)

    annotation class STOMP(val isDefault: kotlin.Boolean = false)

    annotation class Redis(val isDefault: kotlin.Boolean = false)

    annotation class Mercure(val isDefault: kotlin.Boolean = false)
}
