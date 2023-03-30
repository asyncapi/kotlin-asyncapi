package org.openfolder.kotlinasyncapi.springweb.context.annotation.processor

import org.junit.jupiter.api.Test
import org.openfolder.kotlinasyncapi.annotation.Tag
import org.openfolder.kotlinasyncapi.annotation.channel.Channel
import org.openfolder.kotlinasyncapi.annotation.channel.Message
import org.openfolder.kotlinasyncapi.annotation.channel.Parameter
import org.openfolder.kotlinasyncapi.annotation.channel.Publish
import org.openfolder.kotlinasyncapi.annotation.channel.SecurityRequirement
import org.openfolder.kotlinasyncapi.annotation.channel.Subscribe
import org.openfolder.kotlinasyncapi.springweb.TestUtils
import kotlin.reflect.full.findAnnotation

internal class ChannelProcessorTest {

    private val processor = ChannelProcessor()

    @Test
    fun `should process channel annotation`() {
        val payload = TestChannel::class
        val annotation = payload.findAnnotation<Channel>()!!

        val expected = TestUtils.json("annotation/channel_component.json")
        val actual = TestUtils.json(processor.process(annotation, payload))

        TestUtils.assertJsonEquals(expected, actual)
    }

    @Channel(
        key = "some/{parameter}/sqs",
        description = "testDescription",
        servers = ["sqs"],
        parameters = [
            Parameter(
                key = "parameter",
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
