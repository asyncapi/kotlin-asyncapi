package org.openfolder.kotlinasyncapi.model

import org.openfolder.kotlinasyncapi.model.AsyncApi.Companion.asyncApi
import org.openfolder.kotlinasyncapi.model.TestUtils.assertJsonEquals
import org.openfolder.kotlinasyncapi.model.TestUtils.json
import org.junit.jupiter.api.Test

internal class AsyncApiIntegrationTest {

    @Test
    fun `should build a async api component`() {
        val asyncApi = asyncApi {
            id("urn:com:gitter:streaming:api")
            info {
                title("Gitter Streaming API")
                version("1.0.0")
            }
            servers {
                server("production") {
                    url("https://stream.gitter.im/v1")
                    protocol("https")
                    protocolVersion("1.1")
                    security {
                        requirement("httpBearerToken" to emptyList())
                    }
                }
            }
            channels {
                channel("/rooms/{roomId}") {
                    parameters {
                        parameter("roomId") {
                            description("Id of the Gitter room.")
                            schema {
                                type("string")
                                examples("53307860c3599d1de448e19d")
                            }
                        }
                    }
                    subscribe {
                        bindings {
                            http {
                                type("response")
                            }
                        }
                        messages {
                            oneOf {
                                reference {
                                    ref("#/components/messages/chatMessage")
                                }
                                reference {
                                    ref("#/components/messages/heartbeat")
                                }
                            }
                        }
                    }
                }
            }
            components {
                securitySchemes {
                    schema("httpBearerToken") {
                        type("http")
                        scheme("bearer")
                    }
                }
                messages {
                    message("chatMessage") {
                        schemaFormat("application/schema+yaml;version=draft-07")
                        summary("A message represents an individual chat message sent to a room.")
                        payload {
                            type("object")
                            properties {
                                schema("id") {
                                    type("string")
                                    description("ID of the message.")
                                }
                                schema("text") {
                                    type("string")
                                    description("Original message in plain-text/markdown.")
                                }
                            }
                        }
                        bindings {
                            reference("http") {
                                ref("#/components/messageBindings/streamingHeaders")
                            }
                        }
                    }
                    message("heartbeat") {
                        schemaFormat("application/schema+yaml;version=draft-07")
                        summary("Its purpose is to keep the connection alive.")
                        payload {
                            type("string")
                            enum("\r\n")
                        }
                        bindings {
                            reference("http") {
                                ref("#/components/messageBindings/streamingHeaders")
                            }
                        }
                    }
                }
                messageBindings {
                    bindings("streamingHeaders") {
                        http {
                            headers {
                                type("object")
                                properties {
                                    schema("Transfer-Encoding") {
                                        type("string")
                                        enum("chunked")
                                    }
                                    schema("Trailer") {
                                        type("string")
                                        enum("\\r\\n")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        val expected = json("async_api_integration.json")
        val actual = json(asyncApi)

        assertJsonEquals(expected, actual)
    }
}
