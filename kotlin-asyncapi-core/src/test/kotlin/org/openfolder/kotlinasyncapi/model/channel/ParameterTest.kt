package org.openfolder.kotlinasyncapi.model.channel

import org.openfolder.kotlinasyncapi.model.TestUtils.assertJsonEquals
import org.openfolder.kotlinasyncapi.model.TestUtils.json
import org.junit.jupiter.api.Test

internal class ParameterTest {

    @Test
    fun `should build a parameter component`() {
        val parameter = Parameter().apply {
            description("descriptionValue")
            schema { }
            location("locationValue")
        }
        val expected = json("channel/parameter.json")
        val actual = json(parameter)

        assertJsonEquals(expected, actual)
    }
}
