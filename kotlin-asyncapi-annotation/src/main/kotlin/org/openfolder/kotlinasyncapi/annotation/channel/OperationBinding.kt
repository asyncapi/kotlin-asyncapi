package org.openfolder.kotlinasyncapi.annotation.channel

import org.openfolder.kotlinasyncapi.annotation.Schema

annotation class OperationBindings(
    val default: Boolean = false,
    val reference: String = "",
    val http: OperationBinding.HTTP = OperationBinding.HTTP(default = true, type = ""),
    val ws: OperationBinding.WebSockets = OperationBinding.WebSockets(default = true),
    val kafka: OperationBinding.Kafka = OperationBinding.Kafka(default = true),
    val anypointmq: OperationBinding.AnypointMQ = OperationBinding.AnypointMQ(default = true),
    val amqp: OperationBinding.AMQP = OperationBinding.AMQP(default = true),
    val amqp1: OperationBinding.AMQP1 = OperationBinding.AMQP1(default = true),
    val mqtt: OperationBinding.MQTT = OperationBinding.MQTT(default = true),
    val mqtt5: OperationBinding.MQTT5 = OperationBinding.MQTT5(default = true),
    val nats: OperationBinding.NATS = OperationBinding.NATS(default = true),
    val jms: OperationBinding.JMS = OperationBinding.JMS(default = true),
    val sns: OperationBinding.SNS = OperationBinding.SNS(default = true),
    val solace: OperationBinding.Solace = OperationBinding.Solace(default = true),
    val sqs: OperationBinding.SQS = OperationBinding.SQS(default = true),
    val stomp: OperationBinding.STOMP = OperationBinding.STOMP(default = true),
    val redis: OperationBinding.Redis = OperationBinding.Redis(default = true),
    val mercure: OperationBinding.Mercure = OperationBinding.Mercure(default = true),
)

sealed interface OperationBinding {

    annotation class AMQP(
        val default: Boolean = false,
        val expiration: Int = 0,
        val userId: String = "",
        val cc: Array<String> = [],
        val bcc: Array<String> = [],
        val replyTo: String = "",
        val timestamp: Boolean = false,
        val ack: Boolean = false,
        val priority: Int = 0,
        val deliveryMode: Int = 0,
        val mandatory: Boolean = false,
        val bindingVersion: String = ""
    )

    annotation class HTTP(
        val default: Boolean = false,
        val type: String,
        val method: String = "",
        val query: Schema = Schema(default = true),
        val bindingVersion: String = ""
    )

    annotation class Kafka(
        val default: Boolean = false,
        val bindingVersion: String = "",
        val groupId: Schema = Schema(default = true),
        val clientId: Schema = Schema(default = true)
    )

    annotation class MQTT(
        val default: Boolean = false,
        val qos: Int = 0,
        val retain: Boolean = false,
        val bindingVersion: String = ""
    )

    annotation class NATS(
        val default: Boolean = false,
        val queue: String = "",
        val bindingVersion: String = ""
    )


    annotation class Solace(
        val default: Boolean = false,
        val bindingVersion: String = "",
        val destinations: Array<SolaceDestination> = []
    )

    annotation class SolaceDestination(
        val default: Boolean = false,
        val destinationType: String = "",
        val deliveryMode: String = "",
        val queue: SolaceDestinationQueue = SolaceDestinationQueue(default = true),
        val topic: SolaceDestinationTopic = SolaceDestinationTopic(default = true)
    )

    annotation class SolaceDestinationQueue(
        val default: Boolean = false,
        val name: String = "",
        val accessType: String = "",
        val topicSubscriptions: Array<String> = []
    )

    annotation class SolaceDestinationTopic(
        val default: Boolean = false,
        val topicSubscriptions: Array<String> = []
    )

    annotation class WebSockets(val default: Boolean = false)

    annotation class AnypointMQ(val default: Boolean = false)

    annotation class AMQP1(val default: Boolean = false)

    annotation class MQTT5(val default: Boolean = false)

    annotation class JMS(val default: Boolean = false)

    annotation class SNS(val default: Boolean = false)

    annotation class SQS(val default: Boolean = false)

    annotation class STOMP(val default: Boolean = false)

    annotation class Redis(val default: Boolean = false)

    annotation class Mercure(val default: Boolean = false)
}
