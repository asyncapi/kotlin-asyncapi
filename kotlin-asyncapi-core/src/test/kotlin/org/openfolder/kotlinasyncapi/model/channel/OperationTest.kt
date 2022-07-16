package org.openfolder.kotlinasyncapi.model.channel

import io.mockk.clearConstructorMockk
import io.mockk.every
import io.mockk.mockkConstructor
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.openfolder.kotlinasyncapi.model.ExternalDocumentation
import org.openfolder.kotlinasyncapi.model.TestUtils.assertJsonEquals
import org.openfolder.kotlinasyncapi.model.TestUtils.json

internal class OperationTest {

    @AfterEach
    fun clearMocks() {
        clearConstructorMockk(ExternalDocumentation::class)
    }

    @Test
    fun `should build a operation component`() {
        mockkConstructor(ExternalDocumentation::class)
        every {
            anyConstructed<ExternalDocumentation>().url
        } returns "mocked"

        val operation = Operation().apply {
            operationId("operationIdValue")
            summary("summaryValue")
            description("descriptionValue")
            security { }
            tags { }
            externalDocs { }
            bindings { }
            traits { }
            message { }
        }
        val expected = json("channel/operation.json")
        val actual = json(operation)

        assertJsonEquals(expected, actual)
    }
}
