package com.asyncapi.kotlinasyncapi.model.channel

import io.mockk.clearConstructorMockk
import io.mockk.every
import io.mockk.mockkConstructor
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import com.asyncapi.kotlinasyncapi.model.ExternalDocumentation
import com.asyncapi.kotlinasyncapi.model.TestUtils.assertJsonEquals
import com.asyncapi.kotlinasyncapi.model.TestUtils.json

internal class OperationTraitTest {

    @AfterEach
    fun clearMocks() {
        clearConstructorMockk(ExternalDocumentation::class)
    }

    @Test
    fun `should build a operation trait component`() {
        mockkConstructor(ExternalDocumentation::class)
        every {
            anyConstructed<ExternalDocumentation>().url
        } returns "mocked"

        val operationTrait = OperationTrait().apply {
            operationId("operationIdValue")
            summary("summaryValue")
            description("descriptionValue")
            tags { }
            externalDocs { }
            bindings { }
        }
        val expected = json("channel/operation_trait.json")
        val actual = json(operationTrait)

        assertJsonEquals(expected, actual)
    }
}
