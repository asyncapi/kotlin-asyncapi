package org.openfolder.kotlinasyncapi.annotation.channel

import org.openfolder.kotlinasyncapi.annotation.Boolean
import org.openfolder.kotlinasyncapi.annotation.Int
import org.openfolder.kotlinasyncapi.annotation.Schema

annotation class ChannelBindings(
    val isDefault: kotlin.Boolean = false,
    val http: ChannelBinding.HTTP = ChannelBinding.HTTP(isDefault = true),
    val ws: ChannelBinding.WebSockets = ChannelBinding.WebSockets(isDefault = true),
    val kafka: ChannelBinding.Kafka = ChannelBinding.Kafka(isDefault = true),
    val anypointmq: ChannelBinding.AnypointMQ = ChannelBinding.AnypointMQ(isDefault = true),
    val amqp: ChannelBinding.AMQP = ChannelBinding.AMQP(isDefault = true),
    val amqp1: ChannelBinding.AMQP1 = ChannelBinding.AMQP1(isDefault = true),
    val mqtt: ChannelBinding.MQTT = ChannelBinding.MQTT(isDefault = true),
    val mqtt5: ChannelBinding.MQTT5 = ChannelBinding.MQTT5(isDefault = true),
    val nats: ChannelBinding.NATS = ChannelBinding.NATS(isDefault = true),
    val jms: ChannelBinding.JMS = ChannelBinding.JMS(isDefault = true),
    val sns: ChannelBinding.SNS = ChannelBinding.SNS(isDefault = true),
    val solace: ChannelBinding.Solace = ChannelBinding.Solace(isDefault = true),
    val sqs: ChannelBinding.SQS = ChannelBinding.SQS(isDefault = true),
    val stomp: ChannelBinding.STOMP = ChannelBinding.STOMP(isDefault = true),
    val redis: ChannelBinding.Redis = ChannelBinding.Redis(isDefault = true),
    val mercure: ChannelBinding.Mercure = ChannelBinding.Mercure(isDefault = true),
    val ibmmq: ChannelBinding.IBMMQ = ChannelBinding.IBMMQ(isDefault = true)
)

sealed interface ChannelBinding {

    annotation class AnypointMQ(
        val isDefault: kotlin.Boolean = false,
        val destination: String = "",
        val destinationType: String = "",
        val bindingVersion: String = ""
    )

    annotation class AMQP(
        val isDefault: kotlin.Boolean = false,
        val `is`: String = "",
        val bindingVersion: String = "",
        val exchange: AMQPExchange = AMQPExchange(isDefault = true),
        val queue: AMQPQueue = AMQPQueue(isDefault = true)
    )

    annotation class AMQPExchange(
        val isDefault: kotlin.Boolean = false,
        val name: String = "",
        val type: String = "",
        val durable: Boolean = Boolean(isDefault = true),
        val autoDelete: Boolean = Boolean(isDefault = true),
        val vhost: String = ""
    )

    annotation class AMQPQueue(
        val isDefault: kotlin.Boolean = false,
        val name: String = "",
        val type: String = "",
        val durable: Boolean = Boolean(isDefault = true),
        val exclusive: Boolean = Boolean(isDefault = true),
        val autoDelete: Boolean = Boolean(isDefault = true),
        val vhost: String = ""
    )

    annotation class IBMMQ(
        val isDefault: kotlin.Boolean = false,
        val destinationType: String = "",
        val bindingVersion: String = "",
        val maxMsgLength: Int = Int(isDefault = true),
        val queue: IBMMQQueue = IBMMQQueue(isDefault = true, objectName = ""),
        val topic: IBMMQTopic = IBMMQTopic(isDefault = true)
    )

    annotation class IBMMQQueue(
        val isDefault: kotlin.Boolean = false,
        val objectName: String,
        val isPartitioned: Boolean = Boolean(isDefault = true),
        val exclusive: Boolean = Boolean(isDefault = true)
    )

    annotation class IBMMQTopic(
        val isDefault: kotlin.Boolean = false,
        val string: String = "",
        val objectName: String = "",
        val durablePermitted: Boolean = Boolean(isDefault = true),
        val lastMsgRetained: Boolean = Boolean(isDefault = true)
    )

    annotation class WebSockets(
        val isDefault: kotlin.Boolean = false,
        val method: String = "",
        val bindingVersion: String = "",
        val query: Schema = Schema(isDefault = true),
        val headers: Schema = Schema(isDefault = true)
    )

    annotation class HTTP(val isDefault: kotlin.Boolean = false)

    annotation class Kafka(val isDefault: kotlin.Boolean = false)

    annotation class AMQP1(val isDefault: kotlin.Boolean = false)

    annotation class MQTT(val isDefault: kotlin.Boolean = false)

    annotation class MQTT5(val isDefault: kotlin.Boolean = false)

    annotation class NATS(val isDefault: kotlin.Boolean = false)

    annotation class JMS(val isDefault: kotlin.Boolean = false)

    annotation class SNS(val isDefault: kotlin.Boolean = false)

    annotation class Solace(val isDefault: kotlin.Boolean = false)

    annotation class SQS(val isDefault: kotlin.Boolean = false)

    annotation class STOMP(val isDefault: kotlin.Boolean = false)

    annotation class Redis(val isDefault: kotlin.Boolean = false)

    annotation class Mercure(val isDefault: kotlin.Boolean = false)
}
