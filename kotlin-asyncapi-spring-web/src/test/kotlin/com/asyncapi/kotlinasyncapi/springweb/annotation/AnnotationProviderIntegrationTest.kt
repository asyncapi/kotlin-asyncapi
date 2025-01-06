package com.asyncapi.kotlinasyncapi.springweb.annotation

import org.junit.jupiter.api.Test
import com.asyncapi.kotlinasyncapi.annotation.ExternalDocumentation
import com.asyncapi.kotlinasyncapi.annotation.Schema
import com.asyncapi.kotlinasyncapi.annotation.Tag
import com.asyncapi.kotlinasyncapi.annotation.channel.Channel
import com.asyncapi.kotlinasyncapi.annotation.channel.ChannelBinding
import com.asyncapi.kotlinasyncapi.annotation.channel.ChannelBindings
import com.asyncapi.kotlinasyncapi.annotation.channel.Message
import com.asyncapi.kotlinasyncapi.annotation.channel.MessageBinding
import com.asyncapi.kotlinasyncapi.annotation.channel.MessageBindings
import com.asyncapi.kotlinasyncapi.annotation.channel.MessageExample
import com.asyncapi.kotlinasyncapi.annotation.channel.OperationBinding
import com.asyncapi.kotlinasyncapi.annotation.channel.OperationBindings
import com.asyncapi.kotlinasyncapi.annotation.channel.Parameter
import com.asyncapi.kotlinasyncapi.annotation.channel.Subscribe
import com.asyncapi.kotlinasyncapi.context.annotation.AnnotationProvider
import com.asyncapi.kotlinasyncapi.springweb.EnableAsyncApi
import com.asyncapi.kotlinasyncapi.springweb.TestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class AnnotationProviderIntegrationTest {

    @Autowired
    private lateinit var annotationProvider: AnnotationProvider

    @Test
    fun `should provide annotation components`() {
        val expected = TestUtils.json("annotation_integration.json")
        val actual = TestUtils.json(annotationProvider.asyncApi?.components!!)

        TestUtils.assertJsonEquals(expected, actual)
    }

    @SpringBootConfiguration
    @EnableAutoConfiguration
    @EnableAsyncApi
    open class TestConfig

    @Channel(
        value = "test/{parameter}/channel",
        description = "testDescription",
        parameters = [Parameter("parameter")],
        bindings = ChannelBindings(amqp = ChannelBinding.AMQP(bindingVersion = "1.0"))
    )
    class TestChannel {

        @Subscribe(
            description = "testDescription",
            bindings = OperationBindings(amqp = OperationBinding.AMQP(bindingVersion = "1.0")),
            message = Message(TestMessage::class)
        )
        fun testOperation() {}
    }

    @Message(
        messageId = "testMessageId",
        name = "testName",
        description = "testDescription",
        headers = Schema(TestHeaders::class),
        externalDocs = ExternalDocumentation(url = "http://example.com"),
        bindings = MessageBindings(amqp = MessageBinding.AMQP(bindingVersion = "1.0")),
        examples = [MessageExample(headers = "{\"type\":\"TEST\"}", payload = "{\"body\":\"body test\"}")],
        tags = [Tag(name = "testName")]
    )
    data class TestMessage(
        val id: Int = 0,
        val name: String,
        val isTest: Boolean
    )

    @Schema
    data class TestHeaders(
        val type: String
    )
}
