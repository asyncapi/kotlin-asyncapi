package org.openfolder.kotlinasyncapi.model.server

import org.junit.jupiter.api.Test
import org.openfolder.kotlinasyncapi.model.TestUtils.assertJsonEquals
import org.openfolder.kotlinasyncapi.model.TestUtils.json

internal class ServerTest {

    @Test
    fun `should build a server component`() {
        val server = Server().apply {
            url("urlValue")
            protocol("protocolValue")
            protocolVersion("protocolVersionValue")
            description("descriptionValue")
            variables { }
            security {
                requirement("requirementKey" to listOf("requirementValue"))
            }
            bindings { }
        }
        val expected = json("server/server.json")
        val actual = json(server)

        assertJsonEquals(expected, actual)
    }
}
