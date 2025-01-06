package com.asyncapi.kotlinasyncapi.model

import org.junit.jupiter.api.Test
import com.asyncapi.kotlinasyncapi.model.TestUtils.assertJsonEquals
import com.asyncapi.kotlinasyncapi.model.TestUtils.json

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
