package org.openfolder.kotlinasyncapi.springweb.controller

import org.junit.jupiter.api.Test
import org.openfolder.kotlinasyncapi.annotation.channel.Channel
import org.openfolder.kotlinasyncapi.annotation.channel.Message
import org.openfolder.kotlinasyncapi.annotation.channel.Publish
import org.openfolder.kotlinasyncapi.springweb.EnableAsyncApi
import org.openfolder.kotlinasyncapi.springweb.TestUtils
import org.openfolder.kotlinasyncapi.springweb.service.AsyncApiExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content

@SpringBootTest
@AutoConfigureMockMvc
internal class AsyncApiControllerIntegrationTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `should return AsyncApi document`() {
        val expected = TestUtils.json("async_api_integration.json")

        mockMvc.perform(get("/docs/asyncapi"))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
            .andExpect(content().json(expected))
    }

    @SpringBootConfiguration
    @EnableAutoConfiguration
    @EnableAsyncApi
    open class TestConfig {

        @Bean
        open fun asyncApiExtension() =
            AsyncApiExtension.builder {
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

        @Bean
        open fun asyncApiExtension2() =
            AsyncApiExtension.builder(order = 10) {
                info {
                    description("Gitter Streaming API Description")
                }
            }

        @Bean
        open fun asyncApiExtension3() =
            AsyncApiExtension.builder(order = 20) {
                info {
                    title("Gitter Streaming API")
                    version("1.0.0")
                    description("Gitter Streaming API Final Description")
                }
            }
    }
}

@SpringBootTest
@AutoConfigureMockMvc
internal class AsyncApiControllerAnnotationIntegrationTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `should return AsyncApi document`() {
        val expected = TestUtils.json("async_api_annotation_integration.json")

        mockMvc.perform(get("/docs/asyncapi"))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
            .andExpect(content().json(expected))
    }

    @SpringBootConfiguration
    @EnableAutoConfiguration
    @EnableAsyncApi
    open class TestConfig {

        @Bean
        open fun asyncApiExtension() =
            AsyncApiExtension.builder {
                info {
                    title("testTitle")
                    version("testVersion")
                }
            }
    }

    @Channel("my/channel")
    class TestChannel {

        @Publish(
            description = "testDescription",
            message = Message(TestMessage::class)
        )
        fun testOperation() {}
    }

    @Message
    data class TestMessage(
        val value: String,
        val optionalValue: Boolean?
    )
}
