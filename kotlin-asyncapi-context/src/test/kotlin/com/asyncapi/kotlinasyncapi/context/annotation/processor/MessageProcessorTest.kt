package com.asyncapi.kotlinasyncapi.context.annotation.processor

import org.junit.jupiter.api.Test
import com.asyncapi.kotlinasyncapi.annotation.Schema
import com.asyncapi.kotlinasyncapi.annotation.Tag
import com.asyncapi.kotlinasyncapi.annotation.channel.Message
import com.asyncapi.kotlinasyncapi.context.TestUtils.assertJsonEquals
import com.asyncapi.kotlinasyncapi.context.TestUtils.json
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