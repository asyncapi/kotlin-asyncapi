package org.openfolder.kotlinasyncapi.springweb.context.external

import org.junit.jupiter.api.Test
import org.openfolder.kotlinasyncapi.annotation.ExternalDocumentation
import org.openfolder.kotlinasyncapi.annotation.Schema
import org.openfolder.kotlinasyncapi.annotation.Tag
import org.openfolder.kotlinasyncapi.annotation.channel.Channel
import org.openfolder.kotlinasyncapi.annotation.channel.ChannelBinding
import org.openfolder.kotlinasyncapi.annotation.channel.ChannelBindings
import org.openfolder.kotlinasyncapi.annotation.channel.Message
import org.openfolder.kotlinasyncapi.annotation.channel.MessageBinding
import org.openfolder.kotlinasyncapi.annotation.channel.MessageBindings
import org.openfolder.kotlinasyncapi.annotation.channel.MessageExample
import org.openfolder.kotlinasyncapi.annotation.channel.OperationBinding
import org.openfolder.kotlinasyncapi.annotation.channel.OperationBindings
import org.openfolder.kotlinasyncapi.annotation.channel.Parameter
import org.openfolder.kotlinasyncapi.annotation.channel.Subscribe
import org.openfolder.kotlinasyncapi.springweb.EnableAsyncApi
import org.openfolder.kotlinasyncapi.springweb.TestUtils
import org.openfolder.kotlinasyncapi.springweb.context.AnnotationProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class AnnotationProviderTest {

    @Autowired
    private lateinit var annotationProvider: AnnotationProvider

    @Test
    fun `should provide annotation components`() {
        val expected = TestUtils.json("annotation/annotation_integration.json")
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
