package org.openfolder.kotlinasyncapi.annotation.channel

import org.openfolder.kotlinasyncapi.annotation.Schema

annotation class MessageBindings(
    val default: Boolean = false,
    val http: MessageBinding.HTTP = MessageBinding.HTTP(default = true),
    val ws: MessageBinding.WebSockets = MessageBinding.WebSockets(default = true),
    val kafka: MessageBinding.Kafka = MessageBinding.Kafka(default = true),
    val anypointmq: MessageBinding.AnypointMQ = MessageBinding.AnypointMQ(default = true),
    val amqp: MessageBinding.AMQP = MessageBinding.AMQP(default = true),
    val amqp1: MessageBinding.AMQP1 = MessageBinding.AMQP1(default = true),
    val mqtt: MessageBinding.MQTT = MessageBinding.MQTT(default = true),
    val mqtt5: MessageBinding.MQTT5 = MessageBinding.MQTT5(default = true),
    val nats: MessageBinding.NATS = MessageBinding.NATS(default = true),
    val jms: MessageBinding.JMS = MessageBinding.JMS(default = true),
    val sns: MessageBinding.SNS = MessageBinding.SNS(default = true),
    val solace: MessageBinding.Solace = MessageBinding.Solace(default = true),
    val sqs: MessageBinding.SQS = MessageBinding.SQS(default = true),
    val stomp: MessageBinding.STOMP = MessageBinding.STOMP(default = true),
    val redis: MessageBinding.Redis = MessageBinding.Redis(default = true),
    val mercure: MessageBinding.Mercure = MessageBinding.Mercure(default = true),
    val ibmmq: MessageBinding.IBMMQ = MessageBinding.IBMMQ(default = true)
)

sealed interface MessageBinding {

    annotation class HTTP(
        val default: Boolean = false,
        val headers: Schema = Schema(default = true),
        val bindingVersion: String = ""
    )

    annotation class Kafka(
        val default: Boolean = false,
        val key: Schema = Schema(default = true),
        val bindingVersion: String = ""
    )

    annotation class AnypointMQ(
        val default: Boolean = false,
        val headers: Schema = Schema(default = true),
        val bindingVersion: String = ""
    )

    annotation class AMQP(
        val default: Boolean = false,
        val contentEncoding: String = "",
        val messageType: String = "",
        val bindingVersion: String = ""
    )

    annotation class MQTT(
        val default: Boolean = false,
        val bindingVersion: String = ""
    )

    annotation class IBMMQ(
        val default: Boolean = false,
        val type: String = "",
        val headers: String = "",
        val description: String = "",
        val expiry: Int = 0,
        val bindingVersion: String = ""
    )

    annotation class WebSockets(val default: Boolean = false)

    annotation class AMQP1(val default: Boolean = false)

    annotation class MQTT5(val default: Boolean = false)

    annotation class NATS(val default: Boolean = false)

    annotation class JMS(val default: Boolean = false)

    annotation class SNS(val default: Boolean = false)

    annotation class Solace(val default: Boolean = false)

    annotation class SQS(val default: Boolean = false)

    annotation class STOMP(val default: Boolean = false)

    annotation class Redis(val default: Boolean = false)

    annotation class Mercure(val default: Boolean = false)
}
