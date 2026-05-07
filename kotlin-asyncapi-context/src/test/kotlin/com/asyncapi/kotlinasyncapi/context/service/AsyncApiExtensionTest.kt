package com.asyncapi.kotlinasyncapi.context.service

import com.asyncapi.kotlinasyncapi.model.AsyncApi
import java.util.concurrent.atomic.AtomicInteger
import kotlin.script.experimental.host.toScriptSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

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
                channels { }
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
                info {
                    title("titleValue")
                    version("versionValue")
                }
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
    fun `should extend from supplied resource`() {
        val invocations = AtomicInteger(0)
        val extension = AsyncApiExtension.from {
            invocations.incrementAndGet()
            AsyncApi.asyncApi {
                info {
                    title("titleValue")
                    version("versionValue")
                }
            }
        }

        val first = extension.extend(AsyncApi())
        val second = extension.extend(AsyncApi())

        assertThat(first.info.title).isEqualTo("titleValue")
        assertThat(second.info.title).isEqualTo("titleValue")
        assertThat(invocations.get()).isEqualTo(1)
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
                channels { }
            }
        )

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected)
    }
}