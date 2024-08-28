package com.asyncapi.kotlinasyncapi.model.info

import org.junit.jupiter.api.Test
import com.asyncapi.kotlinasyncapi.model.TestUtils.assertJsonEquals
import com.asyncapi.kotlinasyncapi.model.TestUtils.json

internal class LicenseTest {

    @Test
    fun `should build a license component`() {
        val license = License().apply {
            name("nameValue")
            url("urlValue")
        }
        val expected = json("info/license.json")
        val actual = json(license)

        assertJsonEquals(expected, actual)
    }
}
