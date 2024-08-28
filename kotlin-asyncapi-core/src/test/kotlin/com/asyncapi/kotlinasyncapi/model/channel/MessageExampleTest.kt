package com.asyncapi.kotlinasyncapi.model.channel

import org.junit.jupiter.api.Test
import com.asyncapi.kotlinasyncapi.model.TestUtils.assertJsonEquals
import com.asyncapi.kotlinasyncapi.model.TestUtils.json

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
