package com.asyncapi.kotlinasyncapi.model.channel

import com.asyncapi.kotlinasyncapi.model.AsyncApiComponent
import com.asyncapi.kotlinasyncapi.model.Reference
import com.asyncapi.kotlinasyncapi.model.Schema

@AsyncApiComponent
class ReferencableOperationBindingsMap : LinkedHashMap<String, Any>() {
    inline fun bindings(key: String, build: OperationBindings.() -> Unit): OperationBindings =
        OperationBindings().apply(build).also { put(key, it) }

    inline fun reference(key: String, build: Reference.() -> Unit): Reference =
        Reference().apply(build).also { put(key, it) }
}

@AsyncApiComponent
class OperationBindings {
    var http: OperationBinding.HTTP? = null
    var ws: OperationBinding.WebSockets? = null
    var kafka: OperationBinding.Kafka? = null
    var anypointmq: OperationBinding.AnypointMQ? = null
    var amqp: OperationBinding.AMQP? = null
    var amqp1: OperationBinding.AMQP1? = null
    var mqtt: OperationBinding.MQTT? = null
    var mqtt5: OperationBinding.MQTT5? = null
    var nats: OperationBinding.NATS? = null
    var jms: OperationBinding.JMS? = null
    var sns: OperationBinding.SNS? = null
    var solace: OperationBinding.Solace? = null
    var sqs: OperationBinding.SQS? = null
    var stomp: OperationBinding.STOMP? = null
    var redis: OperationBinding.Redis? = null
    var mercure: OperationBinding.Mercure? = null

    inline fun http(build: OperationBinding.HTTP.() -> Unit): OperationBinding.HTTP =
        OperationBinding.HTTP().apply(build).also { http = it }

    inline fun ws(build: OperationBinding.WebSockets.() -> Unit): OperationBinding.WebSockets =
        OperationBinding.WebSockets.apply(build).also { ws = it }

    inline fun kafka(build: OperationBinding.Kafka.() -> Unit): OperationBinding.Kafka =
        OperationBinding.Kafka().apply(build).also { kafka = it }

    inline fun anypointmq(build: OperationBinding.AnypointMQ.() -> Unit): OperationBinding.AnypointMQ =
        OperationBinding.AnypointMQ.apply(build).also { anypointmq = it }

    inline fun amqp(build: OperationBinding.AMQP.() -> Unit): OperationBinding.AMQP =
        OperationBinding.AMQP().apply(build).also { amqp = it }

    inline fun amqp1(build: OperationBinding.AMQP1.() -> Unit): OperationBinding.AMQP1 =
        OperationBinding.AMQP1.apply(build).also { amqp1 = it }

    inline fun mqtt(build: OperationBinding.MQTT.() -> Unit): OperationBinding.MQTT =
        OperationBinding.MQTT().apply(build).also { mqtt = it }

    inline fun mqtt5(build: OperationBinding.MQTT5.() -> Unit): OperationBinding.MQTT5 =
        OperationBinding.MQTT5.apply(build).also { mqtt5 = it }

    inline fun nats(build: OperationBinding.NATS.() -> Unit): OperationBinding.NATS =
        OperationBinding.NATS().apply(build).also { nats = it }

    inline fun jms(build: OperationBinding.JMS.() -> Unit): OperationBinding.JMS =
        OperationBinding.JMS.apply(build).also { jms = it }

    inline fun sns(build: OperationBinding.SNS.() -> Unit): OperationBinding.SNS =
        OperationBinding.SNS.apply(build).also { sns = it }

    inline fun solace(build: OperationBinding.Solace.() -> Unit): OperationBinding.Solace =
        OperationBinding.Solace().apply(build).also { solace = it }

    inline fun sqs(build: OperationBinding.SQS.() -> Unit): OperationBinding.SQS =
        OperationBinding.SQS.apply(build).also { sqs = it }

    inline fun stomp(build: OperationBinding.STOMP.() -> Unit): OperationBinding.STOMP =
        OperationBinding.STOMP.apply(build).also { stomp = it }

    inline fun redis(build: OperationBinding.Redis.() -> Unit): OperationBinding.Redis =
        OperationBinding.Redis.apply(build).also { redis = it }

    inline fun mercure(build: OperationBinding.Mercure.() -> Unit): OperationBinding.Mercure =
        OperationBinding.Mercure.apply(build).also { mercure = it }
}

@AsyncApiComponent
sealed interface OperationBinding {

    class HTTP : OperationBinding {
        lateinit var type: String
        var method: String? = null
        var query: Any? = null
        var bindingVersion: String? = null

        fun type(value: String): String =
            value.also { type = it }

        fun method(value: String): String =
            value.also { method = it }

        inline fun query(build: Schema.() -> Unit): Schema =
            Schema().apply(build).also { query = it }

        inline fun queryRef(build: Reference.() -> Unit): Reference =
            Reference().apply(build).also { query = it }

        fun bindingVersion(value: String): String =
            value.also { bindingVersion = it }
    }

    class Kafka : OperationBinding {
        var groupId: Any? = null
        var clientId: Any? = null
        var bindingVersion: String? = null

        inline fun groupId(build: Schema.() -> Unit): Schema =
            Schema().apply(build).also { groupId = it }

        inline fun groupIdRef(build: Reference.() -> Unit): Reference =
            Reference().apply(build).also { groupId = it }

        inline fun clientId(build: Schema.() -> Unit): Schema =
            Schema().apply(build).also { clientId = it }

        inline fun clientIdRef(build: Reference.() -> Unit): Reference =
            Reference().apply(build).also { clientId = it }

        fun bindingVersion(value: String): String =
            value.also { bindingVersion = it }
    }

    class AMQP : OperationBinding {
        var expiration: Int? = null
        var userId: String? = null
        var cc: List<String>? = null
        var priority: Int? = null
        var deliveryMode: Int? = null
        var mandatory: Boolean? = null
        var bcc: List<String>? = null
        var replyTo: String? = null
        var timestamp: Boolean? = null
        var ack: Boolean? = null
        var bindingVersion: String? = null

        fun expiration(value: Int): Int =
            value.also { expiration = it }

        fun userId(value: String): String =
            value.also { userId = it }

        fun cc(vararg values: String): List<String> =
            listOf(*values).also { cc = it }

        fun priority(value: Int): Int =
            value.also { priority = it }

        fun deliveryMode(value: Int): Int =
            value.also { deliveryMode = it }

        fun mandatory(value: Boolean): Boolean =
            value.also { mandatory = it }

        fun bcc(vararg values: String): List<String> =
            listOf(*values).also { bcc = it }

        fun replyTo(value: String): String =
            value.also { replyTo = it }

        fun timestamp(value: Boolean): Boolean =
            value.also { timestamp = it }

        fun ack(value: Boolean): Boolean =
            value.also { ack = it }

        fun bindingVersion(value: String): String =
            value.also { bindingVersion = it }
    }

    class MQTT : OperationBinding {
        var qos: Int? = null
        var retain: Boolean? = null
        var bindingVersion: String? = null

        fun qos(value: Int): Int =
            value.also { qos = it }

        fun retain(value: Boolean): Boolean =
            value.also { retain = it }

        fun bindingVersion(value: String): String =
            value.also { bindingVersion = it }
    }

    class NATS : OperationBinding {
        var queue: String? = null
        var bindingVersion: String? = null

        fun queue(value: String): String =
            value.also { queue = it }

        fun bindingVersion(value: String): String =
            value.also { bindingVersion = it }
    }

    class Solace : OperationBinding {
        var bindingVersion: String? = null
        var destinations: DestinationsList? = null

        fun bindingVersion(value: String): String =
            value.also { bindingVersion = it }

        inline fun destinations(build: DestinationsList.() -> Unit): DestinationsList =
            DestinationsList().apply(build).also { destinations = it }

        @AsyncApiComponent
        class DestinationsList : ArrayList<Destination>() {
            inline fun destination(build: Destination.() -> Unit): Destination =
                Destination().apply(build).also { add(it) }
        }

        @AsyncApiComponent
        class Destination {
            var destinationType: String? = null
            var deliveryMode: String? = null
            var queue: Queue? = null
            var topic: Topic? = null

            fun destinationType(value: String): String =
                value.also { destinationType = it }

            fun deliveryMode(value: String): String =
                value.also { deliveryMode = it }

            inline fun queue(build: Queue.() -> Unit): Queue =
                Queue().apply(build).also { queue = it }

            inline fun topic(build: Topic.() -> Unit): Topic =
                Topic().apply(build).also { topic = it }
        }

        @AsyncApiComponent
        class Queue {
            var name: String? = null
            var topicSubscriptions: List<String>? = null
            var accessType: String? = null

            fun name(value: String): String =
                value.also { name = it }

            fun topicSubscriptions(vararg values: String): List<String> =
                listOf(*values).also { topicSubscriptions = it }

            fun accessType(value: String): String =
                value.also { accessType = it }
        }

        @AsyncApiComponent
        class Topic {
            var topicSubscriptions: List<String>? = null

            fun topicSubscriptions(vararg values: String): List<String> =
                listOf(*values).also { topicSubscriptions = it }
        }
    }

    data object WebSockets : OperationBinding

    data object AnypointMQ : OperationBinding

    data object AMQP1 : OperationBinding

    data object MQTT5 : OperationBinding

    data object JMS : OperationBinding

    data object SNS : OperationBinding

    data object SQS : OperationBinding

    data object STOMP : OperationBinding

    data object Redis : OperationBinding

    data object Mercure : OperationBinding
}
