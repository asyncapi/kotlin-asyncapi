package org.openfolder.kotlinasyncapi.model.info

import org.openfolder.kotlinasyncapi.model.TestUtils.assertJsonEquals
import org.openfolder.kotlinasyncapi.model.TestUtils.json
import io.mockk.clearConstructorMockk
import io.mockk.every
import io.mockk.mockkConstructor
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

internal class InfoTest {

    @AfterEach
    fun clearMocks() {
        clearConstructorMockk(License::class)
    }

    @Test
    fun `should build a info component`() {
        mockkConstructor(License::class)
        every {
            anyConstructed<License>().name
        } returns "mocked"

        val info = Info().apply {
            title("titleValue")
            version("versionValue")
            description("descriptionValue")
            termsOfService("termsOfServiceValue")
            contact { }
            license { }
        }
        val expected = json("info/info.json")
        val actual = json(info)

        assertJsonEquals(expected, actual)
    }
}
