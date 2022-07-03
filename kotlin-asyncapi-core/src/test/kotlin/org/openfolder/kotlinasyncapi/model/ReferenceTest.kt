package org.openfolder.kotlinasyncapi.model

import org.openfolder.kotlinasyncapi.model.TestUtils.assertJsonEquals
import org.openfolder.kotlinasyncapi.model.TestUtils.json
import org.junit.jupiter.api.Test

internal class ReferenceTest {

    @Test
    fun `should build a reference component`() {
        val reference = Reference().apply {
            ref("refValue")
        }
        val expected = json("reference.json")
        val actual = json(reference)

        assertJsonEquals(expected, actual)
    }
}
