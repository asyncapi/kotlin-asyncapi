package org.openfolder.kotlinasyncapi.context.annotation.processor

import org.junit.jupiter.api.Test
import org.openfolder.kotlinasyncapi.annotation.Schema
import org.openfolder.kotlinasyncapi.annotation.Tag
import org.openfolder.kotlinasyncapi.annotation.channel.Message
import org.openfolder.kotlinasyncapi.context.TestUtils.assertJsonEquals
import org.openfolder.kotlinasyncapi.context.TestUtils.json
import kotlin.reflect.full.findAnnotation

internal class MessageProcessorTest {

    private val processor = MessageProcessor()

    @Test
    fun `should process message annotation`() {
        val payload = TestPayload::class
        val annotation = payload.findAnnotation<Message>()!!

        val expected = json("annotation/message_component.json")
        val actual = json(processor.process(annotation, payload))

        assertJsonEquals(expected, actual)
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