package com.asyncapi.kotlinasyncapi.model.channel

import org.junit.jupiter.api.Test
import com.asyncapi.kotlinasyncapi.model.TestUtils.assertJsonEquals
import com.asyncapi.kotlinasyncapi.model.TestUtils.json

internal class ChannelBindingTest {

    @Test
    fun `should build a channel bindings component`() {
        val channelBindings = ChannelBindings().apply {
            amqp {
                `is`("isValue")
                queue {
                    name("nameValue")
                    durable(true)
                    exclusive(true)
                    autoDelete(true)
                    vhost("vhostValue")
                }
                exchange {
                    name("nameValue")
                    type("typeValue")
                    durable(true)
                    autoDelete(true)
                    vhost("vhostValue")
                }
            }
        }
        val expected = json("channel/channel_bindings.json")
        val actual = json(channelBindings)

        assertJsonEquals(expected, actual)
    }
}
