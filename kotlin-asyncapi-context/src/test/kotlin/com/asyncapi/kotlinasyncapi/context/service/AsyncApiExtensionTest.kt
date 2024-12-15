package com.asyncapi.kotlinasyncapi.context.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import com.asyncapi.kotlinasyncapi.model.AsyncApi
import kotlin.script.experimental.host.toScriptSource

internal class AsyncApiExtensionTest {

    @Test
    fun `should extend from builder`() {
        val extension = AsyncApiExtension.builder {
            channels {
                channel("channelKey") {
                    description("descriptionValue")
                }
            }
        }

        val expected = AsyncApi.asyncApi {
            info {
                title("titleValue")
                version("versionValue")
            }
            channels {
                channel("channelKey") {
                    description("descriptionValue")
                }
            }
        }
        val actual = extension.extend(
            AsyncApi.asyncApi {
                info {
                    title("titleValue")
                    version("versionValue")
                }
            }
        )

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected)
    }

    @Test
    fun `should extend from resource`() {
        val extension = AsyncApiExtension.from(
            resource = AsyncApi.asyncApi {
                info {
                    title("titleValue")
                    version("versionValue")
                }
                channels {
                    channel("channelKey") {
                        description("descriptionValue")
                    }
                }
            }
        )

        val expected = AsyncApi.asyncApi {
            info {
                title("titleValue")
                version("versionValue")
            }
            channels {
                channel("channelKey") {
                    description("descriptionValue")
                }
            }
        }
        val actual = extension.extend(
            AsyncApi.asyncApi {
                channels {
                    channel("oldChannelKey") {
                        description("oldDescriptionValue")
                    }
                }
            }
        )

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected)
    }

    @Test
    fun `should extend from script`() {
        val extension = AsyncApiExtension.from(
            script = """
                channels {
                    channel("channelKey") {
                        description("descriptionValue")
                    }
                }
            """.trimIndent().toScriptSource()
        )

        val expected = AsyncApi.asyncApi {
            info {
                title("titleValue")
                version("versionValue")
            }
            channels {
                channel("channelKey") {
                    description("descriptionValue")
                }
            }
        }
        val actual = extension.extend(
            AsyncApi.asyncApi {
                info {
                    title("titleValue")
                    version("versionValue")
                }
            }
        )

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected)
    }
}