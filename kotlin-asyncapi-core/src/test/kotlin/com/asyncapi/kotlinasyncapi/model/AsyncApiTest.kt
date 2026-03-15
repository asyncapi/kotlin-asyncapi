package com.asyncapi.kotlinasyncapi.model

import com.asyncapi.kotlinasyncapi.model.info.Info
import io.mockk.clearConstructorMockk
import io.mockk.every
import io.mockk.mockkConstructor
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows

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

    @Test
    fun `should fail fast when mandatory fields are missing`() {
        val api = AsyncApi.asyncApi {
        }

        val exception = assertThrows(IllegalStateException::class.java) {
            api.validateForSerialization()
        }

        assertTrue(exception.message!!.contains("Missing required properties"))
        assertTrue(exception.message!!.contains("info"))
        assertTrue(exception.message!!.contains("channels"))
    }

    @Test
    fun `should succeed when mandatory fields are initialized`() {
        val api = AsyncApi.asyncApi {
            info {
                title = "Test API"
                version = "1.0.0"
            }
            channels { }
        }

        assertNotNull(api)
    }
}
