package org.openfolder.kotlinasyncapi.springweb.context.annotation.processor

import org.junit.jupiter.api.Test
import org.openfolder.kotlinasyncapi.annotation.Schema
import org.openfolder.kotlinasyncapi.annotation.Tag
import org.openfolder.kotlinasyncapi.annotation.channel.Message
import org.openfolder.kotlinasyncapi.springweb.TestUtils
import kotlin.reflect.full.findAnnotation

internal class MessageProcessorTest {

    private val processor = MessageProcessor()

    @Test
    fun `should process message annotation`() {
        val payload = TestPayload::class
        val annotation = payload.findAnnotation<Message>()!!

        val expected = TestUtils.json("annotation/message_component.json")
        val actual = TestUtils.json(processor.process(annotation, payload))

        TestUtils.assertJsonEquals(expected, actual)
    }

    @Message(
        messageId = "testMessageId",
        name = "testName",
        description = "testDescription",
        tags = [Tag(name = "testName")],
        headers = Schema(TestHeaders::class)
    )
    data class TestPayload(
        val id: Int = 0,
        val name: String,
        val isTest: Boolean
    )

    @Schema
    data class TestHeaders(
        val TYPE: String
    )
}
