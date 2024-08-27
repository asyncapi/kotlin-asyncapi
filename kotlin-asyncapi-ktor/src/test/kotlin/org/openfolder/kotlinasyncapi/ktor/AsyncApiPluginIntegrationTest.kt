package org.openfolder.kotlinasyncapi.ktor

import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.server.application.install
import io.ktor.server.testing.testApplication
import org.junit.jupiter.api.Test
import org.openfolder.kotlinasyncapi.annotation.ExternalDocumentation
import org.openfolder.kotlinasyncapi.annotation.Schema
import org.openfolder.kotlinasyncapi.annotation.Tag
import org.openfolder.kotlinasyncapi.annotation.channel.Channel
import org.openfolder.kotlinasyncapi.annotation.channel.ChannelBinding
import org.openfolder.kotlinasyncapi.annotation.channel.ChannelBindings
import org.openfolder.kotlinasyncapi.annotation.channel.Message
import org.openfolder.kotlinasyncapi.annotation.channel.MessageBinding
import org.openfolder.kotlinasyncapi.annotation.channel.MessageBindings
import org.openfolder.kotlinasyncapi.annotation.channel.MessageExample
import org.openfolder.kotlinasyncapi.annotation.channel.OperationBinding
import org.openfolder.kotlinasyncapi.annotation.channel.OperationBindings
import org.openfolder.kotlinasyncapi.annotation.channel.Parameter
import org.openfolder.kotlinasyncapi.annotation.channel.Subscribe
import org.openfolder.kotlinasyncapi.context.service.AsyncApiExtension

class AsyncApiPluginIntegrationTest {

    @Test
    fun `should return AsyncApi document`() = testApplication {
        application {
            install(AsyncApiPlugin) {
                extensions = listOf(
                    asyncApiExtension1,
                    asyncApiExtension2,
                    asyncApiExtension3
                )
            }
        }

        val expected = TestUtils.json("async_api_integration.json")
        val result = client.get("/docs/asyncapi").bodyAsText()

        TestUtils.assertJsonEquals(expected, result)
    }
}


val asyncApiExtension1 = AsyncApiExtension.builder {
    id("urn:com:gitter:streaming:api")
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
                bindingsRef {
                    ref("#/components/messageBindings/streamingHeaders")
                }
            }
            message("heartbeat") {
                schemaFormat("application/schema+yaml;version=draft-07")
                summary("Its purpose is to keep the connection alive.")
                payload {
                    type("string")
                    enum("\r\n")
                }
                bindingsRef {
                    ref("#/components/messageBindings/streamingHeaders")
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

val asyncApiExtension2 = AsyncApiExtension.builder(order = 10) {
    info {
        description("Gitter Streaming API Description")
    }
}

val asyncApiExtension3 = AsyncApiExtension.builder(order = 20) {
    info {
        title("Gitter Streaming API")
        version("1.0.0")
        description("Gitter Streaming API Final Description")
    }
}

@Channel(
    value = "test/{parameter}/channel",
    description = "testDescription",
    parameters = [Parameter("parameter")],
    bindings = ChannelBindings(amqp = ChannelBinding.AMQP(bindingVersion = "1.0"))
)
class TestChannel {

    @Subscribe(
        description = "testDescription",
        bindings = OperationBindings(amqp = OperationBinding.AMQP(bindingVersion = "1.0")),
        message = Message(TestMessage::class)
    )
    fun testOperation() {}
}

@Message(
    messageId = "testMessageId",
    name = "testName",
    description = "testDescription",
    headers = Schema(TestHeaders::class),
    externalDocs = ExternalDocumentation(url = "http://example.com"),
    bindings = MessageBindings(amqp = MessageBinding.AMQP(bindingVersion = "1.0")),
    examples = [MessageExample(headers = "{\"type\":\"TEST\"}", payload = "{\"body\":\"body test\"}")],
    tags = [Tag(name = "testName")]
)
data class TestMessage(
    val id: Int = 0,
    val name: String,
    val isTest: Boolean
)

@Schema
data class TestHeaders(
    val type: String
)
