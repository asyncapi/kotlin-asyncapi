package com.asyncapi.kotlinasyncapi.model.channel

import io.mockk.clearConstructorMockk
import io.mockk.every
import io.mockk.mockkConstructor
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import com.asyncapi.kotlinasyncapi.model.CorrelationID
import com.asyncapi.kotlinasyncapi.model.ExternalDocumentation
import com.asyncapi.kotlinasyncapi.model.TestUtils.assertJsonEquals
import com.asyncapi.kotlinasyncapi.model.TestUtils.json

internal class MessageTest {

    @AfterEach
    fun clearMocks() {
        clearConstructorMockk(CorrelationID::class, ExternalDocumentation::class)
    }

    @Test
    fun `should build a message component`() {
        mockkConstructor(CorrelationID::class)
        mockkConstructor(ExternalDocumentation::class)
        every {
            anyConstructed<CorrelationID>().location
        } returns "mocked"
        every {
            anyConstructed<ExternalDocumentation>().url
        } returns "mocked"

        val message = Message().apply {
            messageId("messageIdValue")
            headers { }
            payload { }
            correlationId { }
            schemaFormat("schemaFormatValue")
            contentType("contentTypeValue")
            name("nameValue")
            title("titleValue")
            summary("summaryValue")
            description("descriptionValue")
            tags { }
            externalDocs { }
            bindings { }
            examples { }
            traits { }
        }
        val expected = json("channel/message.json")
        val actual = json(message)

        assertJsonEquals(expected, actual)
    }
}
