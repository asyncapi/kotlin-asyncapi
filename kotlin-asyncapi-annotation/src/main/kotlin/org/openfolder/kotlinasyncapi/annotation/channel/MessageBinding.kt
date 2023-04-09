package org.openfolder.kotlinasyncapi.annotation.channel

import org.openfolder.kotlinasyncapi.annotation.Schema
import org.openfolder.kotlinasyncapi.annotation.Int

annotation class MessageBindings(
    val isDefault: Boolean = false,
    val http: MessageBinding.HTTP = MessageBinding.HTTP(isDefault = true),
    val ws: MessageBinding.WebSockets = MessageBinding.WebSockets(isDefault = true),
    val kafka: MessageBinding.Kafka = MessageBinding.Kafka(isDefault = true),
    val anypointmq: MessageBinding.AnypointMQ = MessageBinding.AnypointMQ(isDefault = true),
    val amqp: MessageBinding.AMQP = MessageBinding.AMQP(isDefault = true),
    val amqp1: MessageBinding.AMQP1 = MessageBinding.AMQP1(isDefault = true),
    val mqtt: MessageBinding.MQTT = MessageBinding.MQTT(isDefault = true),
    val mqtt5: MessageBinding.MQTT5 = MessageBinding.MQTT5(isDefault = true),
    val nats: MessageBinding.NATS = MessageBinding.NATS(isDefault = true),
    val jms: MessageBinding.JMS = MessageBinding.JMS(isDefault = true),
    val sns: MessageBinding.SNS = MessageBinding.SNS(isDefault = true),
    val solace: MessageBinding.Solace = MessageBinding.Solace(isDefault = true),
    val sqs: MessageBinding.SQS = MessageBinding.SQS(isDefault = true),
    val stomp: MessageBinding.STOMP = MessageBinding.STOMP(isDefault = true),
    val redis: MessageBinding.Redis = MessageBinding.Redis(isDefault = true),
    val mercure: MessageBinding.Mercure = MessageBinding.Mercure(isDefault = true),
    val ibmmq: MessageBinding.IBMMQ = MessageBinding.IBMMQ(isDefault = true)
)

sealed interface MessageBinding {

    annotation class HTTP(
        val isDefault: Boolean = false,
        val headers: Schema = Schema(isDefault = true),
        val bindingVersion: String = ""
    )

    annotation class Kafka(
        val isDefault: Boolean = false,
        val key: Schema = Schema(isDefault = true),
        val bindingVersion: String = ""
    )

    annotation class AnypointMQ(
        val isDefault: Boolean = false,
        val headers: Schema = Schema(isDefault = true),
        val bindingVersion: String = ""
    )

    annotation class AMQP(
        val isDefault: Boolean = false,
        val contentEncoding: String = "",
        val messageType: String = "",
        val bindingVersion: String = ""
    )

    annotation class MQTT(
        val isDefault: Boolean = false,
        val bindingVersion: String = ""
    )

    annotation class IBMMQ(
        val isDefault: Boolean = false,
        val type: String = "",
        val headers: String = "",
        val description: String = "",
        val expiry: Int = Int(isDefault = true),
        val bindingVersion: String = ""
    )

    annotation class WebSockets(val isDefault: Boolean = false)

    annotation class AMQP1(val isDefault: Boolean = false)

    annotation class MQTT5(val isDefault: Boolean = false)

    annotation class NATS(val isDefault: Boolean = false)

    annotation class JMS(val isDefault: Boolean = false)

    annotation class SNS(val isDefault: Boolean = false)

    annotation class Solace(val isDefault: Boolean = false)

    annotation class SQS(val isDefault: Boolean = false)

    annotation class STOMP(val isDefault: Boolean = false)

    annotation class Redis(val isDefault: Boolean = false)

    annotation class Mercure(val isDefault: Boolean = false)
}
