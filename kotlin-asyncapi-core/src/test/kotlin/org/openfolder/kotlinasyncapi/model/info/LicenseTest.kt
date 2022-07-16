package org.openfolder.kotlinasyncapi.model.info

import org.junit.jupiter.api.Test
import org.openfolder.kotlinasyncapi.model.TestUtils.assertJsonEquals
import org.openfolder.kotlinasyncapi.model.TestUtils.json

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
