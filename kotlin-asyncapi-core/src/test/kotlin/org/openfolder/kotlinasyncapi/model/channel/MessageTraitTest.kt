package org.openfolder.kotlinasyncapi.model.channel

import io.mockk.clearConstructorMockk
import io.mockk.every
import io.mockk.mockkConstructor
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.openfolder.kotlinasyncapi.model.CorrelationID
import org.openfolder.kotlinasyncapi.model.ExternalDocumentation
import org.openfolder.kotlinasyncapi.model.TestUtils.assertJsonEquals
import org.openfolder.kotlinasyncapi.model.TestUtils.json

internal class MessageTraitTest {

    @AfterEach
    fun clearMocks() {
        clearConstructorMockk(CorrelationID::class, ExternalDocumentation::class)
    }

    @Test
    fun `should build a message trait component`() {
        mockkConstructor(CorrelationID::class)
        mockkConstructor(ExternalDocumentation::class)
        every {
            anyConstructed<CorrelationID>().location
        } returns "mocked"
        every {
            anyConstructed<ExternalDocumentation>().url
        } returns "mocked"

        val messageTrait = MessageTrait().apply {
            headers { }
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
        }
        val expected = json("channel/message_trait.json")
        val actual = json(messageTrait)

        assertJsonEquals(expected, actual)
    }
}
