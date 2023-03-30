package org.openfolder.kotlinasyncapi.annotation.channel

import org.openfolder.kotlinasyncapi.annotation.Schema

annotation class ChannelBindings(
    val default: Boolean = false,
    val http: ChannelBinding.HTTP = ChannelBinding.HTTP(default = true),
    val ws: ChannelBinding.WebSockets = ChannelBinding.WebSockets(default = true),
    val kafka: ChannelBinding.Kafka = ChannelBinding.Kafka(default = true),
    val anypointmq: ChannelBinding.AnypointMQ = ChannelBinding.AnypointMQ(default = true),
    val amqp: ChannelBinding.AMQP = ChannelBinding.AMQP(default = true),
    val amqp1: ChannelBinding.AMQP1 = ChannelBinding.AMQP1(default = true),
    val mqtt: ChannelBinding.MQTT = ChannelBinding.MQTT(default = true),
    val mqtt5: ChannelBinding.MQTT5 = ChannelBinding.MQTT5(default = true),
    val nats: ChannelBinding.NATS = ChannelBinding.NATS(default = true),
    val jms: ChannelBinding.JMS = ChannelBinding.JMS(default = true),
    val sns: ChannelBinding.SNS = ChannelBinding.SNS(default = true),
    val solace: ChannelBinding.Solace = ChannelBinding.Solace(default = true),
    val sqs: ChannelBinding.SQS = ChannelBinding.SQS(default = true),
    val stomp: ChannelBinding.STOMP = ChannelBinding.STOMP(default = true),
    val redis: ChannelBinding.Redis = ChannelBinding.Redis(default = true),
    val mercure: ChannelBinding.Mercure = ChannelBinding.Mercure(default = true),
    val ibmmq: ChannelBinding.IBMMQ = ChannelBinding.IBMMQ(default = true)
)

sealed interface ChannelBinding {

    annotation class AnypointMQ(
        val default: Boolean = false,
        val destination: String = "",
        val destinationType: String = "",
        val bindingVersion: String = ""
    )

    annotation class AMQP(
        val default: Boolean = false,
        val `is`: String = "",
        val bindingVersion: String = "",
        val exchange: AMQPExchange = AMQPExchange(default = true),
        val queue: AMQPQueue = AMQPQueue(default = true)
    )

    annotation class AMQPExchange(
        val default: Boolean = false,
        val name: String = "",
        val type: String = "",
        val durable: Boolean = false,
        val autoDelete: Boolean = false,
        val vhost: String = ""
    )

    annotation class AMQPQueue(
        val default: Boolean = false,
        val name: String = "",
        val type: String = "",
        val durable: Boolean = false,
        val exclusive: Boolean = false,
        val autoDelete: Boolean = false,
        val vhost: String = ""
    )

    annotation class IBMMQ(
        val default: Boolean = false,
        val destinationType: String = "",
        val bindingVersion: String = "",
        val maxMsgLength: Int = 0,
        val queue: IBMMQQueue = IBMMQQueue(default = true, objectName = ""),
        val topic: IBMMQTopic = IBMMQTopic(default = true)
    )

    annotation class IBMMQQueue(
        val default: Boolean = false,
        val objectName: String,
        val isPartitioned: Boolean = false,
        val exclusive: Boolean = false
    )

    annotation class IBMMQTopic(
        val default: Boolean = false,
        val string: String = "",
        val objectName: String = "",
        val durablePermitted: Boolean = false,
        val lastMsgRetained: Boolean = false
    )

    annotation class WebSockets(
        val default: Boolean = false,
        val method: String = "",
        val bindingVersion: String = "",
        val query: Schema = Schema(default = true),
        val headers: Schema = Schema(default = true)
    )

    annotation class HTTP(val default: Boolean = false)

    annotation class Kafka(val default: Boolean = false)

    annotation class AMQP1(val default: Boolean = false)

    annotation class MQTT(val default: Boolean = false)

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
