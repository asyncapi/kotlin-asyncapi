package org.openfolder.kotlinasyncapi.springweb.context.external

import org.junit.jupiter.api.Test
import org.openfolder.kotlinasyncapi.annotation.ExternalDocumentation
import org.openfolder.kotlinasyncapi.annotation.Schema
import org.openfolder.kotlinasyncapi.annotation.Tag
import org.openfolder.kotlinasyncapi.annotation.channel.Message
import org.openfolder.kotlinasyncapi.annotation.channel.MessageBinding
import org.openfolder.kotlinasyncapi.annotation.channel.MessageBindings
import org.openfolder.kotlinasyncapi.annotation.channel.MessageExample
import org.openfolder.kotlinasyncapi.springweb.EnableAsyncApi
import org.openfolder.kotlinasyncapi.springweb.TestUtils
import org.openfolder.kotlinasyncapi.springweb.context.DefaultAnnotationProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class DefaultAnnotationProviderTest {

    @Autowired
    private lateinit var annotationProvider: DefaultAnnotationProvider

    @Test
    fun `should provide annotation components`() {
        val expected = TestUtils.json("annotation/annotation_integration.json")
        val actual = TestUtils.json(annotationProvider.components!!)

        TestUtils.assertJsonEquals(expected, actual)
    }

    @SpringBootConfiguration
    @EnableAutoConfiguration
    @EnableAsyncApi
    open class TestConfig

    @Message(
        messageId = "testMessageId",
        name = "testName",
        description = "testDescription",
        headers = Schema(TestHeaders::class),
        externalDocs = ExternalDocumentation(url = "http://example.com"),
        bindings = MessageBindings(http = MessageBinding.HTTP(bindingVersion = "1.0")),
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
