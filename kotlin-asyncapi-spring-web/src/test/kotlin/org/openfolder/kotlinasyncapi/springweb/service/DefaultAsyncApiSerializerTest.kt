package org.openfolder.kotlinasyncapi.springweb.service

import org.junit.jupiter.api.Test
import org.openfolder.kotlinasyncapi.model.AsyncApi
import org.openfolder.kotlinasyncapi.springweb.TestUtils.json
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode

internal class DefaultAsyncApiSerializerTest {

    private val serializer = DefaultAsyncApiSerializer()

    @Test
    fun `should serialize AsyncApi document`() {
        val asyncApi = AsyncApi().apply {
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
        val expected = json("async_api_serialization.json")
        val actual = with(serializer) { asyncApi.serialize() }

        JSONAssert.assertEquals(expected, actual, JSONCompareMode.NON_EXTENSIBLE)
    }
}
