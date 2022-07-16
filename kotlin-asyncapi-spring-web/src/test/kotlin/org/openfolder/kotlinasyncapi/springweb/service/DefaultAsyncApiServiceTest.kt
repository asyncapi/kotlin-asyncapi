package org.openfolder.kotlinasyncapi.springweb.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.openfolder.kotlinasyncapi.model.AsyncApi

@ExtendWith(MockKExtension::class)
internal class DefaultAsyncApiServiceTest {

    @MockK
    private lateinit var asyncApiExtension: AsyncApiExtension

    @InjectMockKs
    private lateinit var asyncApiService: DefaultAsyncApiService

    @Test
    fun `should extend AsyncApi document`() {
        val asyncApi = slot<AsyncApi>()
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
