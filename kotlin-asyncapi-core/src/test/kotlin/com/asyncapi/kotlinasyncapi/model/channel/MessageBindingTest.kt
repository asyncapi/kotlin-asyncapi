package com.asyncapi.kotlinasyncapi.model.channel

import org.junit.jupiter.api.Test
import com.asyncapi.kotlinasyncapi.model.TestUtils.assertJsonEquals
import com.asyncapi.kotlinasyncapi.model.TestUtils.json

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
