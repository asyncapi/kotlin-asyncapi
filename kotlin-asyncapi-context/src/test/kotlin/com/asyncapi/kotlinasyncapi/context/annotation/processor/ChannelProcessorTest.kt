package com.asyncapi.kotlinasyncapi.context.annotation.processor

import org.junit.jupiter.api.Test
import com.asyncapi.kotlinasyncapi.annotation.Tag
import com.asyncapi.kotlinasyncapi.annotation.channel.Channel
import com.asyncapi.kotlinasyncapi.annotation.channel.Message
import com.asyncapi.kotlinasyncapi.annotation.channel.Parameter
import com.asyncapi.kotlinasyncapi.annotation.channel.Publish
import com.asyncapi.kotlinasyncapi.annotation.channel.SecurityRequirement
import com.asyncapi.kotlinasyncapi.annotation.channel.Subscribe
import com.asyncapi.kotlinasyncapi.context.TestUtils.assertJsonEquals
import com.asyncapi.kotlinasyncapi.context.TestUtils.json
import kotlin.reflect.full.findAnnotation

internal class ChannelProcessorTest {

    private val processor = ChannelProcessor()

    @Test
    fun `should process channel annotation`() {
        val payload = TestChannel::class
        val annotation = payload.findAnnotation<Channel>()!!

        val expected = json("annotation/channel_component.json")
        val actual = json(processor.process(annotation, payload))

        assertJsonEquals(expected, actual)
    }

    @Channel(
        value = "some/{parameter}/channel",
        description = "testDescription",
        servers = ["dev"],
        parameters = [
            Parameter(
                value = "parameter",
                description = "testDescription"
            )
        ]
    )
    class TestChannel {

        @Subscribe(
            operationId = "testOperationId",
            security = [
                SecurityRequirement(
                    key = "petstore_auth",
                    values = ["write:pets", "read:pets"]
                )
            ],
            message = Message(TestSubscribeMessage::class)
        )
        fun testSubscribe() {}

        @Publish(
            description = "testDescription",
            tags = [
                Tag(name = "testTag")
            ],
            messages = [
                Message(TestPublishMessage::class),
                Message(TestPublishMessage2::class)
            ]
        )
        fun testPublish() {}
    }

    @Message
    data class TestSubscribeMessage(
        val id: Int = 0,
        val name: String,
        val isTest: Boolean
    )

    @Message
    data class TestPublishMessage(
        val id: Int = 0,
        val name: String,
        val isTest: Boolean
    )

    @Message
    data class TestPublishMessage2(
        val id: Int = 0,
        val name: String,
        val isTest: Boolean
    )
}
