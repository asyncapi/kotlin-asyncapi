package org.openfolder.kotlinasyncapi.model

import org.junit.jupiter.api.Test
import org.openfolder.kotlinasyncapi.model.TestUtils.assertJsonEquals
import org.openfolder.kotlinasyncapi.model.TestUtils.json

internal class ExternalDocumentationTest {

    @Test
    fun `should build a external documentation component`() {
        val externalDoc = ExternalDocumentation().apply {
            description("descriptionValue")
            url("urlValue")
        }
        val expected = json("external_documentation.json")
        val actual = json(externalDoc)

        assertJsonEquals(expected, actual)
    }
}
