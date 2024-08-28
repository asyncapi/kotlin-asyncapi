package com.asyncapi.kotlinasyncapi.model

import io.mockk.clearConstructorMockk
import io.mockk.every
import io.mockk.mockkConstructor
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import com.asyncapi.kotlinasyncapi.model.info.Info

internal class AsyncApiTest {

    @AfterEach
    fun clearMocks() {
        clearConstructorMockk(Info::class, ExternalDocumentation::class)
    }

    @Test
    fun `should build a async api component`() {
        mockkConstructor(Info::class)
        mockkConstructor(ExternalDocumentation::class)
        every {
            anyConstructed<Info>().title
        } returns "mocked"
        every {
            anyConstructed<Info>().version
        } returns "mocked"
        every {
            anyConstructed<ExternalDocumentation>().url
        } returns "mocked"

        val asyncApi = AsyncApi().apply {
            id("idValue")
            defaultContentType("defaultContentTypeValue")
            info { }
            servers { }
            channels { }
            components { }
            tags { }
            externalDocs { }
        }
        val expected = TestUtils.json("async_api.json")
        val actual = TestUtils.json(asyncApi)

        TestUtils.assertJsonEquals(expected, actual)
    }
}
