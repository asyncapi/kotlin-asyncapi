package com.asyncapi.kotlinasyncapi.model.server

import com.asyncapi.kotlinasyncapi.model.AsyncApiComponent
import com.asyncapi.kotlinasyncapi.model.Reference

@AsyncApiComponent
class ReferencableServerBindingsMap : LinkedHashMap<String, Any>() {
    inline fun bindings(key: String, build: ServerBindings.() -> Unit): ServerBindings =
        ServerBindings().apply(build).also { put(key, it) }

    inline fun reference(key: String, build: Reference.() -> Unit): Reference =
        Reference().apply(build).also { put(key, it) }
}

@AsyncApiComponent
class ServerBindings {
    var http: ServerBinding.HTTP? = null
    var ws: ServerBinding.WebSockets? = null
    var kafka: ServerBinding.Kafka? = null
    var anypointmq: ServerBinding.AnypointMQ? = null
    var amqp: ServerBinding.AMQP? = null
    var amqp1: ServerBinding.AMQP1? = null
    var mqtt: ServerBinding.MQTT? = null
    var mqtt5: ServerBinding.MQTT5? = null
    var nats: ServerBinding.NATS? = null
    var jms: ServerBinding.JMS? = null
    var sns: ServerBinding.SNS? = null
    var solace: ServerBinding.Solace? = null
    var sqs: ServerBinding.SQS? = null
    var stomp: ServerBinding.STOMP? = null
    var redis: ServerBinding.Redis? = null
    var mercure: ServerBinding.Mercure? = null
    var ibmmq: ServerBinding.IBMMQ? = null

    inline fun http(build: ServerBinding.HTTP.() -> Unit): ServerBinding.HTTP =
        ServerBinding.HTTP.apply(build).also { http = it }

    inline fun ws(build: ServerBinding.WebSockets.() -> Unit): ServerBinding.WebSockets =
        ServerBinding.WebSockets.apply(build).also { ws = it }

    inline fun kafka(build: ServerBinding.Kafka.() -> Unit): ServerBinding.Kafka =
        ServerBinding.Kafka.apply(build).also { kafka = it }

    inline fun anypointmq(build: ServerBinding.AnypointMQ.() -> Unit): ServerBinding.AnypointMQ =
        ServerBinding.AnypointMQ.apply(build).also { anypointmq = it }

    inline fun amqp(build: ServerBinding.AMQP.() -> Unit): ServerBinding.AMQP =
        ServerBinding.AMQP.apply(build).also { amqp = it }

    inline fun amqp1(build: ServerBinding.AMQP1.() -> Unit): ServerBinding.AMQP1 =
        ServerBinding.AMQP1.apply(build).also { amqp1 = it }

    inline fun mqtt(build: ServerBinding.MQTT.() -> Unit): ServerBinding.MQTT =
        ServerBinding.MQTT().apply(build).also { mqtt = it }

    inline fun mqtt5(build: ServerBinding.MQTT5.() -> Unit): ServerBinding.MQTT5 =
        ServerBinding.MQTT5.apply(build).also { mqtt5 = it }

    inline fun nats(build: ServerBinding.NATS.() -> Unit): ServerBinding.NATS =
        ServerBinding.NATS.apply(build).also { nats = it }

    inline fun jms(build: ServerBinding.JMS.() -> Unit): ServerBinding.JMS =
        ServerBinding.JMS.apply(build).also { jms = it }

    inline fun sns(build: ServerBinding.SNS.() -> Unit): ServerBinding.SNS =
        ServerBinding.SNS.apply(build).also { sns = it }

    inline fun solace(build: ServerBinding.Solace.() -> Unit): ServerBinding.Solace =
        ServerBinding.Solace().apply(build).also { solace = it }

    inline fun sqs(build: ServerBinding.SQS.() -> Unit): ServerBinding.SQS =
        ServerBinding.SQS.apply(build).also { sqs = it }

    inline fun stomp(build: ServerBinding.STOMP.() -> Unit): ServerBinding.STOMP =
        ServerBinding.STOMP.apply(build).also { stomp = it }

    inline fun redis(build: ServerBinding.Redis.() -> Unit): ServerBinding.Redis =
        ServerBinding.Redis.apply(build).also { redis = it }

    inline fun mercure(build: ServerBinding.Mercure.() -> Unit): ServerBinding.Mercure =
        ServerBinding.Mercure.apply(build).also { mercure = it }

    inline fun ibmmq(build: ServerBinding.IBMMQ.() -> Unit): ServerBinding.IBMMQ =
        ServerBinding.IBMMQ().apply(build).also { ibmmq = it }
}

@AsyncApiComponent
sealed interface ServerBinding {

    class MQTT : ServerBinding {
        var clientId: String? = null
        var cleanSession: Boolean? = null
        var lastWill: LastWill? = null
        var keepAlive: Int? = null
        var bindingVersion: String? = null

        fun clientId(value: String): String =
            value.also { clientId = it }

        fun cleanSession(value: Boolean): Boolean =
            value.also { cleanSession = it }

        inline fun lastWill(build: LastWill.() -> Unit): LastWill =
            LastWill().apply(build).also { lastWill = it }

        fun keepAlive(value: Int): Int =
            value.also { keepAlive = it }

        fun bindingVersion(value: String): String =
            value.also { bindingVersion = it }

        class LastWill {
            var topic: String? = null
            var qos: Int? = null
            var message: String? = null
            var retain: Boolean? = null

            fun topic(value: String): String =
                value.also { topic = it }

            fun qos(value: Int): Int =
                value.also { qos = it }

            fun message(value: String): String =
                value.also { message = it }

            fun retain(value: Boolean): Boolean =
                value.also { retain = it }
        }
    }

    class Solace : ServerBinding {
        var bindingVersion: String? = null
        var msgVpn: String? = null

        fun bindingVersion(value: String): String =
            value.also { bindingVersion = it }

        fun msgVpn(value: String): String =
            value.also { msgVpn = it }
    }

    class IBMMQ : ServerBinding {
        var groupId: String? = null
        var ccdtQueueManagerName: String? = null
        var cipherSpec: String? = null
        var multiEndpointServer: Boolean? = null
        var heartBeatInterval: Int? = null
        var bindingVersion: String? = null

        fun groupId(value: String): String =
            value.also { groupId = it }

        fun ccdtQueueManagerName(value: String): String =
            value.also { ccdtQueueManagerName = it }

        fun cipherSpec(value: String): String =
            value.also { cipherSpec = it }

        fun multiEndpointServer(value: Boolean): Boolean =
            value.also { multiEndpointServer = it }

        fun heartBeatInterval(value: Int): Int =
            value.also { heartBeatInterval = it }

        fun bindingVersion(value: String): String =
            value.also { bindingVersion = it }
    }

    data object HTTP : ServerBinding

    data object WebSockets : ServerBinding

    data object Kafka : ServerBinding

    data object AnypointMQ : ServerBinding

    data object AMQP : ServerBinding

    data object AMQP1 : ServerBinding

    data object MQTT5 : ServerBinding

    data object NATS : ServerBinding

    data object JMS : ServerBinding

    data object SNS : ServerBinding

    data object SQS : ServerBinding

    data object STOMP : ServerBinding

    data object Redis : ServerBinding

    data object Mercure : ServerBinding
}
