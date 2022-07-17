package org.openfolder.kotlinasyncapi.springweb.service

import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.slot
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.openfolder.kotlinasyncapi.model.AsyncApi

@ExtendWith(MockKExtension::class)
internal class DefaultAsyncApiServiceTest {

    @Test
    fun `should extend AsyncApi document`() {
        val asyncApi = slot<AsyncApi>()
        val asyncApiExtension = mockk<AsyncApiExtension>()
        val asyncApiExtensions = listOf(asyncApiExtension)
        val asyncApiService = DefaultAsyncApiService(asyncApiExtensions)
        every {
            asyncApiExtension.extend(capture(asyncApi))
        } answers {
            asyncApi.captured.apply {
                id("urn:com:gitter:streaming:api")
                info {
                    title("Gitter Streaming API")
                    version("1.0.0")
                }
                servers {
                    server("production") {
                        url("https://stream.gitter.im/v1")
                        protocol("https")
                    }
                }
                channels {
                    channel("/rooms") { }
                }
            }
        }

        val expected = AsyncApi().apply {
            id("urn:com:gitter:streaming:api")
            info {
                title("Gitter Streaming API")
                version("1.0.0")
            }
            servers {
                server("production") {
                    url("https://stream.gitter.im/v1")
                    protocol("https")
                }
            }
            channels {
                channel("/rooms") { }
            }
        }
        val actual = asyncApiService.asyncApi()

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected)
    }
}
