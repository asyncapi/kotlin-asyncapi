![Logo](.github/assets/kotlin_asyncapi_logo.png)
[![Apache License 2](https://img.shields.io/badge/license-ASF2-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.txt)
[![Build status](https://github.com/OpenFolder/kotlin-asyncapi/actions/workflows/build.yml/badge.svg?branch=master)](https://github.com/OpenFolder/kotlin-asyncapi/actions/workflows/build.yml)
[![Qodana](https://github.com/OpenFolder/kotlin-asyncapi/actions/workflows/qodana.yml/badge.svg?branch=master)](https://openfolder.github.io/kotlin-asyncapi/qodana/report)
[![Maven Central status](https://img.shields.io/maven-central/v/org.openfolder/kotlin-asyncapi-parent.svg)](https://search.maven.org/#search%7Cga%7C1%7Corg.openfolder%20kotlin-asyncapi)

* [About](#about)
* [Module Roadmap](#module-roadmap)
* [Usage](#usage)
    * [Kotlin DSL](#kotlin-dsl)
    * [Spring Web](#spring-web)
    * [Configuration](#configuration)
* [License](#license)

## About
The Kotlin AsyncAPI project aims to provide convenience tools for generating and serving 
[AsyncAPI](https://www.asyncapi.com/) documentation. The core of this project is a 
[Kotlin DSL](https://kotlinlang.org/docs/type-safe-builders.html) for building the specification in a typesafe way. 
The modules around that core build a framework for documenting asynchronous microservice APIs.

## Module Roadmap
| Module                | Description                                                      | State              |
|-----------------------|------------------------------------------------------------------|--------------------|
| **core**              | Kotlin DSL for building AsyncAPI specifications                  | :white_check_mark: |
| **spring&#x2011;web** | Spring Boot autoconfiguration for serving the generated document | :white_check_mark: |
| **script**            | Kotlin scripting support for configuration as code               | :soon:             |
| **template**          | Template engine for reusing similar AsyncAPI components          | :eyes:             |

## Usage
### Kotlin DSL
The `AsyncApi` class represents the root of the specification. It provides a static entry function `asyncApi` to the 
build scope.

**Example** (simplified version of [Gitter example](https://github.com/asyncapi/spec/blob/22c6f2c7a61846338bfbd43d81024cb12cf4ed5f/examples/gitter-streaming.yml))
```kotlin
asyncApi {
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
            }
            message("heartbeat") {
                summary("Its purpose is to keep the connection alive.")
                payload {
                    type("string")
                    enum("\r\n")
                }
            }
        }
    }
}
```

### Spring Web
To serve your AsyncAPI specification via Spring Web:
- enable the autoconfiguration by annotating a configuration class with`@EnableAsyncApi`
- provide an `AsyncApiExtension` bean for the application context

**Example** (simplified version of [Gitter example](https://github.com/asyncapi/spec/blob/22c6f2c7a61846338bfbd43d81024cb12cf4ed5f/examples/gitter-streaming.yml))
```kotlin
@EnableAsyncApi
@Configuration
class AsyncApiConfiguration {
    
    @Bean
    fun asyncApiExtension() =
        AsyncApiExtension.builder {
            info {
                title("Gitter Streaming API")
                version("1.0.0")
            }
            servers {
                // ...
            }
            // ...
        }
}
```

### Configuration
You can disable the autoconfiguration and configure the rest endpoint in the application properties.

**Example**
```yaml
asyncapi:
  enabled: false
  path: /docs/asyncapi
```

## License
Kotlin AsyncAPI is Open Source software released under the
[Apache 2.0 license](http://www.apache.org/licenses/LICENSE-2.0.html).
