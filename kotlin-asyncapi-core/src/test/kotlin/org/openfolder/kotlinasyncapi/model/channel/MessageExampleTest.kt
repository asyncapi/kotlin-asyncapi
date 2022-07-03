package org.openfolder.kotlinasyncapi.model.channel

import org.openfolder.kotlinasyncapi.model.TestUtils.assertJsonEquals
import org.openfolder.kotlinasyncapi.model.TestUtils.json
import org.junit.jupiter.api.Test

internal class MessageExampleTest {

    @Test
    fun `should build a message example component`() {
        val messageExample = MessageExample().apply {
            headers("headersKey" to "headersValue")
            payload(mapOf("payloadKey" to "payloadValue"))
            name("nameValue")
            summary("summaryValue")
        }
        val expected = json("channel/message_example.json")
        val actual = json(messageExample)

        assertJsonEquals(expected, actual)
    }
}
