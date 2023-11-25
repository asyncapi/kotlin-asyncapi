package org.openfolder.kotlinasyncapi.model.channel

import org.openfolder.kotlinasyncapi.model.AsyncApiComponent
import org.openfolder.kotlinasyncapi.model.Reference
import org.openfolder.kotlinasyncapi.model.Schema

@AsyncApiComponent
class ReferencableMessageBindingsMap : LinkedHashMap<String, Any>() {
    inline fun bindings(key: String, build: MessageBindings.() -> Unit): MessageBindings =
        MessageBindings().apply(build).also { put(key, it) }

    inline fun reference(key: String, build: Reference.() -> Unit): Reference =
        Reference().apply(build).also { put(key, it) }
}

@AsyncApiComponent
class MessageBindings {
    var http: MessageBinding.HTTP? = null
    var ws: MessageBinding.WebSockets? = null
    var kafka: MessageBinding.Kafka? = null
    var anypointmq: MessageBinding.AnypointMQ? = null
    var amqp: MessageBinding.AMQP? = null
    var amqp1: MessageBinding.AMQP1? = null
    var mqtt: MessageBinding.MQTT? = null
    var mqtt5: MessageBinding.MQTT5? = null
    var nats: MessageBinding.NATS? = null
    var jms: MessageBinding.JMS? = null
    var sns: MessageBinding.SNS? = null
    var solace: MessageBinding.Solace? = null
    var sqs: MessageBinding.SQS? = null
    var stomp: MessageBinding.STOMP? = null
    var redis: MessageBinding.Redis? = null
    var mercure: MessageBinding.Mercure? = null
    var ibmmq: MessageBinding.IBMMQ? = null

    inline fun http(build: MessageBinding.HTTP.() -> Unit): MessageBinding.HTTP =
        MessageBinding.HTTP().apply(build).also { http = it }

    inline fun ws(build: MessageBinding.WebSockets.() -> Unit): MessageBinding.WebSockets =
        MessageBinding.WebSockets.apply(build).also { ws = it }

    inline fun kafka(build: MessageBinding.Kafka.() -> Unit): MessageBinding.Kafka =
        MessageBinding.Kafka().apply(build).also { kafka = it }

    inline fun anypointmq(build: MessageBinding.AnypointMQ.() -> Unit): MessageBinding.AnypointMQ =
        MessageBinding.AnypointMQ().apply(build).also { anypointmq = it }

    inline fun amqp(build: MessageBinding.AMQP.() -> Unit): MessageBinding.AMQP =
        MessageBinding.AMQP().apply(build).also { amqp = it }

    inline fun amqp1(build: MessageBinding.AMQP1.() -> Unit): MessageBinding.AMQP1 =
        MessageBinding.AMQP1.apply(build).also { amqp1 = it }

    inline fun mqtt(build: MessageBinding.MQTT.() -> Unit): MessageBinding.MQTT =
        MessageBinding.MQTT().apply(build).also { mqtt = it }

    inline fun mqtt5(build: MessageBinding.MQTT5.() -> Unit): MessageBinding.MQTT5 =
        MessageBinding.MQTT5.apply(build).also { mqtt5 = it }

    inline fun nats(build: MessageBinding.NATS.() -> Unit): MessageBinding.NATS =
        MessageBinding.NATS.apply(build).also { nats = it }

    inline fun jms(build: MessageBinding.JMS.() -> Unit): MessageBinding.JMS =
        MessageBinding.JMS.apply(build).also { jms = it }

    inline fun sns(build: MessageBinding.SNS.() -> Unit): MessageBinding.SNS =
        MessageBinding.SNS.apply(build).also { sns = it }

    inline fun solace(build: MessageBinding.Solace.() -> Unit): MessageBinding.Solace =
        MessageBinding.Solace.apply(build).also { solace = it }

    inline fun sqs(build: MessageBinding.SQS.() -> Unit): MessageBinding.SQS =
        MessageBinding.SQS.apply(build).also { sqs = it }

    inline fun stomp(build: MessageBinding.STOMP.() -> Unit): MessageBinding.STOMP =
        MessageBinding.STOMP.apply(build).also { stomp = it }

    inline fun redis(build: MessageBinding.Redis.() -> Unit): MessageBinding.Redis =
        MessageBinding.Redis.apply(build).also { redis = it }

    inline fun mercure(build: MessageBinding.Mercure.() -> Unit): MessageBinding.Mercure =
        MessageBinding.Mercure.apply(build).also { mercure = it }

    inline fun ibmmq(build: MessageBinding.IBMMQ.() -> Unit): MessageBinding.IBMMQ =
        MessageBinding.IBMMQ().apply(build).also { ibmmq = it }
}

@AsyncApiComponent
sealed interface MessageBinding {

    class HTTP : MessageBinding {
        var headers: Any? = null
        var bindingVersion: String? = null

        inline fun headers(build: Schema.() -> Unit): Schema =
            Schema().apply(build).also { headers = it }

        inline fun headersRef(build: Reference.() -> Unit): Reference =
            Reference().apply(build).also { headers = it }

        fun bindingVersion(value: String): String =
            value.also { bindingVersion = it }
    }

    class Kafka : MessageBinding {
        var key: Any? = null
        var bindingVersion: String? = null

        inline fun key(build: Schema.() -> Unit): Schema =
            Schema().apply(build).also { key = it }

        inline fun keyRef(build: Reference.() -> Unit): Reference =
            Reference().apply(build).also { key = it }

        fun bindingVersion(value: String): String =
            value.also { bindingVersion = it }
    }

    class AnypointMQ : MessageBinding {
        var headers: Any? = null
        var bindingVersion: String? = null

        inline fun headers(build: Schema.() -> Unit): Schema =
            Schema().apply(build).also { headers = it }

        inline fun headersRef(build: Reference.() -> Unit): Reference =
            Reference().apply(build).also { headers = it }

        fun bindingVersion(value: String): String =
            value.also { bindingVersion = it }
    }

    class AMQP : MessageBinding {
        var contentEncoding: String? = null
        var messageType: String? = null
        var bindingVersion: String? = null

        fun contentEncoding(value: String): String =
            value.also { contentEncoding = it }

        fun messageType(value: String): String =
            value.also { messageType = it }

        fun bindingVersion(value: String): String =
            value.also { bindingVersion = it }
    }

    class MQTT : MessageBinding {
        var bindingVersion: String? = null

        fun bindingVersion(value: String): String =
            value.also { bindingVersion = it }
    }

    class IBMMQ : MessageBinding {
        var type: String? = null
        var headers: String? = null
        var description: String? = null
        var expiry: Int? = null
        var bindingVersion: String? = null

        fun type(value: String): String =
            value.also { type = it }

        fun headers(value: String): String =
            value.also { headers = it }

        fun description(value: String): String =
            value.also { description = it }

        fun expiry(value: Int): Int =
            value.also { expiry = it }

        fun bindingVersion(value: String): String =
            value.also { bindingVersion = it }
    }

    data object WebSockets : MessageBinding

    data object AMQP1 : MessageBinding

    data object MQTT5 : MessageBinding

    data object NATS : MessageBinding

    data object JMS : MessageBinding

    data object SNS : MessageBinding

    data object Solace : MessageBinding

    data object SQS : MessageBinding

    data object STOMP : MessageBinding

    data object Redis : MessageBinding

    data object Mercure : MessageBinding
}
