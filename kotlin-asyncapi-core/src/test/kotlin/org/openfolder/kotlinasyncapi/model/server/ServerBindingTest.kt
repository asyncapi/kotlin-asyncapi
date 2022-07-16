package org.openfolder.kotlinasyncapi.model.server

import org.junit.jupiter.api.Test
import org.openfolder.kotlinasyncapi.model.TestUtils.assertJsonEquals
import org.openfolder.kotlinasyncapi.model.TestUtils.json

internal class ServerBindingTest {

    @Test
    fun `should build a server bindings component`() {
        val serverBindings = ServerBindings().apply {
            mqtt {
                clientId("clientIdValue")
                cleanSession(true)
                lastWill {
                    topic("topicValue")
                    qos(1)
                    message("messageValue")
                    retain(true)
                }
                keepAlive(1)
                bindingVersion("bindingVersionValue")
            }
        }
        val expected = json("server/server_bindings.json")
        val actual = json(serverBindings)

        assertJsonEquals(expected, actual)
    }
}
