package com.asyncapi.kotlinasyncapi.springweb.resource

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import com.asyncapi.kotlinasyncapi.context.ResourceProvider
import com.asyncapi.kotlinasyncapi.model.AsyncApi
import com.asyncapi.kotlinasyncapi.springweb.EnableAsyncApi
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext

@SpringBootTest
internal class ResourceProviderIntegrationTest {

    @Autowired
    private lateinit var context: ApplicationContext

    @Test
    fun `should provide parsed JSON resource`() {
        val resourceProvider = ResourceProvider(
            path = "async_api_resource_integration.json"
        )

        val expected = AsyncApi.asyncApi {
            info {
                title("titleValue")
                version("versionValue")
            }
            channels { }
        }
        val actual = resourceProvider.asyncApi

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected)
    }

    @SpringBootConfiguration
    @EnableAutoConfiguration
    @EnableAsyncApi
    open class TestConfig
}
