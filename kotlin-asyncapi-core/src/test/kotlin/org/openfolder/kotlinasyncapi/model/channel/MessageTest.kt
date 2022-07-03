package org.openfolder.kotlinasyncapi.model.channel

import org.openfolder.kotlinasyncapi.model.CorrelationID
import org.openfolder.kotlinasyncapi.model.ExternalDocumentation
import org.openfolder.kotlinasyncapi.model.TestUtils.assertJsonEquals
import org.openfolder.kotlinasyncapi.model.TestUtils.json
import io.mockk.clearConstructorMockk
import io.mockk.every
import io.mockk.mockkConstructor
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

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
