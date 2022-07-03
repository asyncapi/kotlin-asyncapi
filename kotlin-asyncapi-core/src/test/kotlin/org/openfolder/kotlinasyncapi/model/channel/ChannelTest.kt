package org.openfolder.kotlinasyncapi.model.channel

import org.openfolder.kotlinasyncapi.model.TestUtils.assertJsonEquals
import org.openfolder.kotlinasyncapi.model.TestUtils.json
import org.junit.jupiter.api.Test

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
