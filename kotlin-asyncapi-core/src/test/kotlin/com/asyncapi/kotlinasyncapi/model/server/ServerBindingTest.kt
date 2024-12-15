package com.asyncapi.kotlinasyncapi.model.server

import org.junit.jupiter.api.Test
import com.asyncapi.kotlinasyncapi.model.TestUtils.assertJsonEquals
import com.asyncapi.kotlinasyncapi.model.TestUtils.json

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
