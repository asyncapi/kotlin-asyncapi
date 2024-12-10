package org.openfolder.kotlinasyncapi.context.annotation.processor

import org.junit.jupiter.api.Test
import org.openfolder.kotlinasyncapi.annotation.Tag
import org.openfolder.kotlinasyncapi.annotation.channel.Channel
import org.openfolder.kotlinasyncapi.annotation.channel.Message
import org.openfolder.kotlinasyncapi.annotation.channel.Parameter
import org.openfolder.kotlinasyncapi.annotation.channel.Publish
import org.openfolder.kotlinasyncapi.annotation.channel.SecurityRequirement
import org.openfolder.kotlinasyncapi.annotation.channel.Subscribe
import org.openfolder.kotlinasyncapi.context.TestUtils.assertJsonEquals
import org.openfolder.kotlinasyncapi.context.TestUtils.json
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.functions

internal class ChannelProcessorTest {

    private val processor = ChannelProcessor()

    @Test
    fun `should process channel annotation on class`() {
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

    @Test
    fun `should process channel annotation on function`() {
        val payload = TestChannelFunction::class
        val annotation = payload.functions.flatMap { it.annotations }.filterIsInstance<Channel>().firstOrNull()!!

        val expected = json("annotation/channel_component_function.json")
        val actual = json(processor.process(annotation, payload))

        assertJsonEquals(expected, actual)
    }


    class TestChannelFunction {
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
