package org.openfolder.kotlinasyncapi.model.channel

import org.junit.jupiter.api.Test
import org.openfolder.kotlinasyncapi.model.TestUtils.assertJsonEquals
import org.openfolder.kotlinasyncapi.model.TestUtils.json

internal class MessageBindingTest {

    @Test
    fun `should build a message bindings component`() {
        val messageBindings = MessageBindings().apply {
            mqtt {
                bindingVersion("bindingVersionValue")
            }
        }
        val expected = json("channel/message_bindings.json")
        val actual = json(messageBindings)

        assertJsonEquals(expected, actual)
    }
}
