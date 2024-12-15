package com.asyncapi.kotlinasyncapi.model.channel

import com.asyncapi.kotlinasyncapi.model.AsyncApiComponent
import com.asyncapi.kotlinasyncapi.model.Reference
import com.asyncapi.kotlinasyncapi.model.Schema

@AsyncApiComponent
class ReferencableChannelBindingsMap : LinkedHashMap<String, Any>() {
    inline fun bindings(key: String, build: ChannelBindings.() -> Unit): ChannelBindings =
        ChannelBindings().apply(build).also { put(key, it) }

    inline fun reference(key: String, build: Reference.() -> Unit): Reference =
        Reference().apply(build).also { put(key, it) }
}

@AsyncApiComponent
class ChannelBindings {
    var http: ChannelBinding.HTTP? = null
    var ws: ChannelBinding.WebSockets? = null
    var kafka: ChannelBinding.Kafka? = null
    var anypointmq: ChannelBinding.AnypointMQ? = null
    var amqp: ChannelBinding.AMQP? = null
    var amqp1: ChannelBinding.AMQP1? = null
    var mqtt: ChannelBinding.MQTT? = null
    var mqtt5: ChannelBinding.MQTT5? = null
    var nats: ChannelBinding.NATS? = null
    var jms: ChannelBinding.JMS? = null
    var sns: ChannelBinding.SNS? = null
    var solace: ChannelBinding.Solace? = null
    var sqs: ChannelBinding.SQS? = null
    var stomp: ChannelBinding.STOMP? = null
    var redis: ChannelBinding.Redis? = null
    var mercure: ChannelBinding.Mercure? = null
    var ibmmq: ChannelBinding.IBMMQ? = null

    inline fun http(build: ChannelBinding.HTTP.() -> Unit): ChannelBinding.HTTP =
        ChannelBinding.HTTP.apply(build).also { http = it }

    inline fun ws(build: ChannelBinding.WebSockets.() -> Unit): ChannelBinding.WebSockets =
        ChannelBinding.WebSockets().apply(build).also { ws = it }

    inline fun kafka(build: ChannelBinding.Kafka.() -> Unit): ChannelBinding.Kafka =
        ChannelBinding.Kafka.apply(build).also { kafka = it }

    inline fun anypointmq(build: ChannelBinding.AnypointMQ.() -> Unit): ChannelBinding.AnypointMQ =
        ChannelBinding.AnypointMQ().apply(build).also { anypointmq = it }

    inline fun amqp(build: ChannelBinding.AMQP.() -> Unit): ChannelBinding.AMQP =
        ChannelBinding.AMQP().apply(build).also { amqp = it }

    inline fun amqp1(build: ChannelBinding.AMQP1.() -> Unit): ChannelBinding.AMQP1 =
        ChannelBinding.AMQP1.apply(build).also { amqp1 = it }

    inline fun mqtt(build: ChannelBinding.MQTT.() -> Unit): ChannelBinding.MQTT =
        ChannelBinding.MQTT.apply(build).also { mqtt = it }

    inline fun mqtt5(build: ChannelBinding.MQTT5.() -> Unit): ChannelBinding.MQTT5 =
        ChannelBinding.MQTT5.apply(build).also { mqtt5 = it }

    inline fun nats(build: ChannelBinding.NATS.() -> Unit): ChannelBinding.NATS =
        ChannelBinding.NATS.apply(build).also { nats = it }

    inline fun jms(build: ChannelBinding.JMS.() -> Unit): ChannelBinding.JMS =
        ChannelBinding.JMS.apply(build).also { jms = it }

    inline fun sns(build: ChannelBinding.SNS.() -> Unit): ChannelBinding.SNS =
        ChannelBinding.SNS.apply(build).also { sns = it }

    inline fun solace(build: ChannelBinding.Solace.() -> Unit): ChannelBinding.Solace =
        ChannelBinding.Solace.apply(build).also { solace = it }

    inline fun sqs(build: ChannelBinding.SQS.() -> Unit): ChannelBinding.SQS =
        ChannelBinding.SQS.apply(build).also { sqs = it }

    inline fun stomp(build: ChannelBinding.STOMP.() -> Unit): ChannelBinding.STOMP =
        ChannelBinding.STOMP.apply(build).also { stomp = it }

    inline fun redis(build: ChannelBinding.Redis.() -> Unit): ChannelBinding.Redis =
        ChannelBinding.Redis.apply(build).also { redis = it }

    inline fun mercure(build: ChannelBinding.Mercure.() -> Unit): ChannelBinding.Mercure =
        ChannelBinding.Mercure.apply(build).also { mercure = it }

    inline fun ibmmq(build: ChannelBinding.IBMMQ.() -> Unit): ChannelBinding.IBMMQ =
        ChannelBinding.IBMMQ().apply(build).also { ibmmq = it }
}

@AsyncApiComponent
sealed interface ChannelBinding {

    class WebSockets : ChannelBinding {
        var method: String? = null
        var query: Any? = null
        var headers: Any? = null
        var bindingVersion: String? = null

        fun method(value: String): String =
            value.also { method = it }

        inline fun query(build: Schema.() -> Unit): Schema =
            Schema().apply(build).also { query = it }

        inline fun queryRef(build: Reference.() -> Unit): Reference =
            Reference().apply(build).also { query = it }

        inline fun headers(build: Schema.() -> Unit): Schema =
            Schema().apply(build).also { headers = it }

        inline fun headersRef(build: Reference.() -> Unit): Reference =
            Reference().apply(build).also { headers = it }

        fun bindingVersion(value: String): String =
            value.also { bindingVersion = it }
    }

    class AnypointMQ : ChannelBinding {
        var destination: String? = null
        var destinationType: String? = null
        var bindingVersion: String? = null

        fun destination(value: String): String =
            value.also { destination = it }

        fun destinationType(value: String): String =
            value.also { destinationType = it }

        fun bindingVersion(value: String): String =
            value.also { bindingVersion = it }
    }

    class AMQP : ChannelBinding {
        var `is`: String? = null
        var exchange: Exchange? = null
        var queue: Queue? = null
        var bindingVersion: String? = null

        fun `is`(value: String): String =
            value.also { `is` = it }

        inline fun exchange(build: Exchange.() -> Unit): Exchange =
            Exchange().apply(build).also { exchange = it }

        inline fun queue(build: Queue.() -> Unit): Queue =
            Queue().apply(build).also { queue = it }

        fun bindingVersion(value: String): String =
            value.also { bindingVersion = it }

        @AsyncApiComponent
        class Exchange {
            var name: String? = null
            var type: String? = null
            var durable: Boolean? = null
            var autoDelete: Boolean? = null
            var vhost: String? = null

            fun name(value: String): String =
                value.also { name = it }

            fun type(value: String): String =
                value.also { type = it }

            fun durable(value: Boolean): Boolean =
                value.also { durable = it }

            fun autoDelete(value: Boolean): Boolean =
                value.also { autoDelete = it }

            fun vhost(value: String): String =
                value.also { vhost = it }
        }

        @AsyncApiComponent
        class Queue {
            var name: String? = null
            var type: String? = null
            var durable: Boolean? = null
            var exclusive: Boolean? = null
            var autoDelete: Boolean? = null
            var vhost: String? = null

            fun name(value: String): String =
                value.also { name = it }

            fun type(value: String): String =
                value.also { type = it }

            fun durable(value: Boolean): Boolean =
                value.also { durable = it }

            fun exclusive(value: Boolean): Boolean =
                value.also { exclusive = it }

            fun autoDelete(value: Boolean): Boolean =
                value.also { autoDelete = it }

            fun vhost(value: String): String =
                value.also { vhost = it }
        }
    }

    class IBMMQ : ChannelBinding {
        var destinationType: String? = null
        var queue: Queue? = null
        var topic: Topic? = null
        var maxMsgLength: Int? = null
        var bindingVersion: String? = null

        fun destinationType(value: String): String =
            value.also { destinationType = it }

        inline fun queue(build: Queue.() -> Unit): Queue =
            Queue().apply(build).also { queue = it }

        inline fun topic(build: Topic.() -> Unit): Topic =
            Topic().apply(build).also { topic = it }

        fun maxMsgLength(value: Int): Int =
            value.also { maxMsgLength = it }

        fun bindingVersion(value: String): String =
            value.also { bindingVersion = it }

        class Queue {
            lateinit var objectName: String
            var isPartitioned: Boolean? = null
            var exclusive: Boolean? = null

            fun objectName(value: String): String =
                value.also { objectName = it }

            fun isPartitioned(value: Boolean): Boolean =
                value.also { isPartitioned = it }

            fun exclusive(value: Boolean): Boolean =
                value.also { exclusive = it }
        }

        class Topic {
            var string: String? = null
            var objectName: String? = null
            var durablePermitted: Boolean? = null
            var lastMsgRetained: Boolean? = null

            fun string(value: String): String =
                value.also { string = it }

            fun objectName(value: String): String =
                value.also { objectName = it }

            fun durablePermitted(value: Boolean): Boolean =
                value.also { durablePermitted = it }

            fun lastMsgRetained(value: Boolean): Boolean =
                value.also { lastMsgRetained = it }
        }
    }

    data object HTTP : ChannelBinding

    data object Kafka : ChannelBinding

    data object AMQP1 : ChannelBinding

    data object MQTT : ChannelBinding

    data object MQTT5 : ChannelBinding

    data object NATS : ChannelBinding

    data object JMS : ChannelBinding

    data object SNS : ChannelBinding

    data object Solace : ChannelBinding

    data object SQS : ChannelBinding

    data object STOMP : ChannelBinding

    data object Redis : ChannelBinding

    data object Mercure : ChannelBinding
}
