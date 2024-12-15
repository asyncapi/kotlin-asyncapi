package com.asyncapi.kotlinasyncapi.model.channel

import org.junit.jupiter.api.Test
import com.asyncapi.kotlinasyncapi.model.TestUtils.assertJsonEquals
import com.asyncapi.kotlinasyncapi.model.TestUtils.json

internal class ChannelTest {

    @Test
    fun `should build a channel item component`() {
        val channel = Channel().apply {
            ref("refValue")
            description("descriptionValue")
            servers("serversValue")
            subscribe { }
            publish { }
            parameters { }
            bindings { }
        }
        val expected = json("channel/channel.json")
        val actual = json(channel)

        assertJsonEquals(expected, actual)
    }
}
