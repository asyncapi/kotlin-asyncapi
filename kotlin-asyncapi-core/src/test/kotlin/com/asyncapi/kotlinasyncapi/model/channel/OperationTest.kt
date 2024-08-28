package com.asyncapi.kotlinasyncapi.model.channel

import io.mockk.clearConstructorMockk
import io.mockk.every
import io.mockk.mockkConstructor
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import com.asyncapi.kotlinasyncapi.model.ExternalDocumentation
import com.asyncapi.kotlinasyncapi.model.TestUtils.assertJsonEquals
import com.asyncapi.kotlinasyncapi.model.TestUtils.json

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
