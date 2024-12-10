package org.openfolder.kotlinasyncapi.context.annotation.processor

import org.junit.jupiter.api.Test
import org.openfolder.kotlinasyncapi.annotation.AsyncApiDocumentation
import org.openfolder.kotlinasyncapi.annotation.channel.Channel
import org.openfolder.kotlinasyncapi.annotation.channel.Message
import org.openfolder.kotlinasyncapi.annotation.channel.Parameter
import org.openfolder.kotlinasyncapi.annotation.channel.SecurityRequirement
import org.openfolder.kotlinasyncapi.annotation.channel.Subscribe
import org.openfolder.kotlinasyncapi.context.TestUtils.assertJsonEquals
import org.openfolder.kotlinasyncapi.context.TestUtils.json
import kotlin.reflect.full.findAnnotation

internal class AsyncApiDocumentationProcessorTest {

    private val processor = AsyncApiDocumentationProcessor()

    @Test
    fun `should process async api documentation annotation on class`() {
        val payload = TestChannelFunction::class
        val annotation = payload.findAnnotation<AsyncApiDocumentation>()!!

        val expected = json("annotation/async_api_documentation_component.json")
        val actual = json(processor.process(annotation, payload))

        assertJsonEquals(expected, actual)
    }


    @AsyncApiDocumentation
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
}