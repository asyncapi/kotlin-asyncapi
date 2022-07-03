package org.openfolder.kotlinasyncapi.model

import org.openfolder.kotlinasyncapi.model.TestUtils.assertJsonEquals
import org.openfolder.kotlinasyncapi.model.TestUtils.json
import io.mockk.clearConstructorMockk
import io.mockk.every
import io.mockk.mockkConstructor
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

internal class TagTest {

    @AfterEach
    fun clearMocks() {
        clearConstructorMockk(ExternalDocumentation::class)
    }

    @Test
    fun `should build a tag component`() {
        mockkConstructor(ExternalDocumentation::class)
        every {
            anyConstructed<ExternalDocumentation>().url
        } returns "mocked"

        val tag = Tag().apply {
            name("nameValue")
            description("descriptionValue")
            externalDocs { }
        }
        val expected = json("tag.json")
        val actual = json(tag)

        assertJsonEquals(expected, actual)
    }
}
