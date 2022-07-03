package org.openfolder.kotlinasyncapi.model.server

import org.openfolder.kotlinasyncapi.model.TestUtils.assertJsonEquals
import org.openfolder.kotlinasyncapi.model.TestUtils.json
import org.junit.jupiter.api.Test

internal class ServerVariableTest {

    @Test
    fun `should build a server variable component`() {
        val serverVariable = ServerVariable().apply {
            enum("enumValue")
            default("defaultValue")
            description("descriptionValue")
            examples("examplesValue")
        }
        val expected = json("server/server_variables.json")
        val actual = json(serverVariable)

        assertJsonEquals(expected, actual)
    }
}
