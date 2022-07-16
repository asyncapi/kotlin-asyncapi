package org.openfolder.kotlinasyncapi.model

import org.junit.jupiter.api.Test
import org.openfolder.kotlinasyncapi.model.TestUtils.assertJsonEquals
import org.openfolder.kotlinasyncapi.model.TestUtils.json

internal class CorrelationIDTest {

    @Test
    fun `should build a external documentation component`() {
        val correlationID = CorrelationID().apply {
            description("descriptionValue")
            location("locationValue")
        }
        val expected = json("correlation_id.json")
        val actual = json(correlationID)

        assertJsonEquals(expected, actual)
    }
}
