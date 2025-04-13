![Logo](.github/assets/kotlin_asyncapi_logo.png)
[![Apache License 2](https://img.shields.io/badge/license-ASF2-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.txt)
[![Build status](https://github.com/asyncapi/kotlin-asyncapi/actions/workflows/build.yml/badge.svg?branch=master)](https://github.com/asyncapi/kotlin-asyncapi/actions/workflows/build.yml)
[![Qodana](https://github.com/asyncapi/kotlin-asyncapi/actions/workflows/qodana.yml/badge.svg?branch=master)](https://asyncapi.github.io/kotlin-asyncapi/qodana/report)
[![Maven Central status](https://img.shields.io/maven-central/v/org.openfolder/kotlin-asyncapi-parent.svg)](https://search.maven.org/#search%7Cga%7C1%7Corg.openfolder%20kotlin-asyncapi)

> [!NOTE]
> Spring Framework 6 / Spring Boot 3 is supported since `6.0.14` / `3.1.6`

* [About](#about)
* [Usage](#usage)
    * [Kotlin DSL](#kotlin-dsl-usage)
    * [Spring Web](#spring-web-usage)
    * [Ktor](#ktor-usage)
    * [Annotation](#annotation-usage)
    * [Kotlin Script](#kotlin-script-usage)
    * [Examples](#examples)
* [Configuration](#configuration)
    * [Spring Web](#spring-web-configuration)
    * [Ktor](#ktor-configuration)
    * [Maven Plugin](#maven-plugin-configuration)
* [License](#license)

## About
The Kotlin AsyncAPI project aims to provide convenience tools for generating and serving 
[AsyncAPI](https://www.asyncapi.com/) documentation. The core of this project is a 
[Kotlin DSL](https://kotlinlang.org/docs/type-safe-builders.html) for building the specification in a typesafe way. 
The modules around that core build a framework for documenting asynchronous microservice APIs.

## Usage
### <a name="kotlin-dsl-usage"></a>Kotlin DSL
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

### <a name="spring-web-usage"></a>Spring Web
To serve your AsyncAPI specification via Spring Web:
- add the `kotlin-asyncapi-spring-web` dependency
- enable the auto-configuration by annotating a configuration class with `@EnableAsyncApi`
- document your API with `AsyncApiExtension` beans and/or Kotlin scripting (see [Kotlin script usage](#kotlin-script-usage))
- add annotations to auto-generate components (see [annotation usage](#annotation-usage))

You can register multiple extensions to extend and override AsyncAPI components. Extensions with a higher order override extensions with a lower order. Please note that you can only extend top-level components for now (`info`, `channels`, `servers`...). Subcomponents will always be overwritten.

**Example** (simplified version of [Gitter example](https://github.com/asyncapi/spec/blob/22c6f2c7a61846338bfbd43d81024cb12cf4ed5f/examples/gitter-streaming.yml))
```kotlin
@EnableAsyncApi
@Configuration
class AsyncApiConfiguration {
    
    @Bean
    fun asyncApiExtension() =
        AsyncApiExtension.builder(order = 10) {
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

@Channel(
    value = "/rooms/{roomId}",
    parameters = [
        Parameter(
            value = "roomId",
            schema = Schema(
                type = "string",
                examples = ["53307860c3599d1de448e19d"]
            )
        )
    ]
)
class RoomsChannel {

    @Subscribe(message = Message(ChatMessage::class))
    fun publish(/*...*/) { /*...*/ }
}

@Message
data class ChatMessage(
    val id: String,
    val text: String
)
```
```xml
<dependency>
  <groupId>org.openfolder</groupId>
  <artifactId>kotlin-asyncapi-spring-web</artifactId>
  <version>${kotlin-asyncapi.version}</version>
</dependency>
```

### <a name="ktor-usage"></a>Ktor
To serve your AsyncAPI specification via Ktor:
- add the `kotlin-asyncapi-ktor` dependency
- install the `AsyncApiPlugin` in you application
- document your API with `AsyncApiExtension` and/or Kotlin scripting (see [Kotlin script usage](#kotlin-script-usage))
- add annotations to auto-generate components (see [annotation usage](#annotation-usage))

You can register multiple extensions to extend and override AsyncAPI components. Extensions with a higher order override extensions with a lower order. Please note that you can only extend top-level components for now (`info`, `channels`, `servers`...). Subcomponents will always be overwritten.

**Example** (simplified version of [Gitter example](https://github.com/asyncapi/spec/blob/22c6f2c7a61846338bfbd43d81024cb12cf4ed5f/examples/gitter-streaming.yml))
```kotlin
fun main() {
    embeddedServer(Netty, port = 8000) {
        install(AsyncApiPlugin) {
            extension = AsyncApiExtension.builder(order = 10) {
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
    }.start(wait = true)
}

@Channel(
    value = "/rooms/{roomId}",
    parameters = [
        Parameter(
            value = "roomId",
            schema = Schema(
                type = "string",
                examples = ["53307860c3599d1de448e19d"]
            )
        )
    ]
)
class RoomsChannel {

    @Subscribe(message = Message(ChatMessage::class))
    fun publish(/*...*/) { /*...*/ }
}

@Message
data class ChatMessage(
    val id: String,
    val text: String
)
```
```xml
<dependency>
  <groupId>org.openfolder</groupId>
  <artifactId>kotlin-asyncapi-ktor</artifactId>
  <version>${kotlin-asyncapi.version}</version>
</dependency>
```

### <a name="annotation-usage"></a>Annotation
The `kotlin-asyncapi-annotation` module defines technology-agnostic annotations that can be used to document event-driven microservice APIs. 

| Annotation        | Target   | Description                                                                                                                          |
|-------------------|----------|--------------------------------------------------------------------------------------------------------------------------------------|
| Channel           | `class`  | Marks a class that represents a event channel. The value property defines the name of the channel.                                   |
| Subscribe/Publish | `method` | Marks a method that represents a channel operation. The method must be defined inside a channel class.                               |
| Message           | `class`  | Marks a class that represents a message. The value property can be used to reference a class that is annotated with this annotation. |
| Schema            | `class`  | Marks a class that represents a schema. The value property can be used to reference a class that is annotated with this annotation.  |

### <a name="kotlin-script-usage"></a>Kotlin Script
[Kotlin scripting](https://github.com/Kotlin/KEEP/blob/b0c8a37db684eaf74bb1305f3c180b5d2537d787/proposals/scripting-support.md) allows us to execute a piece of code in a provided context. The IDE can still provide features like autocompletion and syntax highlighting. Furthermore, it provides the following benefits:
- separate AsyncAPI documentation from application source code
- focus on AsyncAPI content and don't worry about the build context or spring web integration
- use AsyncAPI Kotlin DSL in Java projects

You have two options to use Kotlin scripting in your project:
- [Plugin] let the Maven plugin evaluate the script during build time (recommended)
- [Embedded] let your Spring Boot application evaluate the script at runtime

### <a name="examples"></a>Examples
- [Spring Boot Application](kotlin-asyncapi-examples/kotlin-asyncapi-spring-boot-example)
- [Ktor Application](kotlin-asyncapi-examples/kotlin-asyncapi-ktor-example)

#### Maven Plugin
The Maven plugin evaluates your `asyncapi.kts` script, generates a valid AsyncAPI JSON file and adds it to the project resources. The `kotlin-asyncapi-spring-web` module picks the generated resource up and converts it to an `AsyncApiExtension`.

By default, the plugin expects the script to be named `build.asyncapi.kts` and placed in the project root. The script path and resource target path can be changed in the plugin configuration.

**Example** (simplified version of [Gitter example](https://github.com/asyncapi/spec/blob/22c6f2c7a61846338bfbd43d81024cb12cf4ed5f/examples/gitter-streaming.yml))
```kotlin
info {
    title("Gitter Streaming API")
    version("1.0.0")
}

servers {
    // ...
}

// ...
```
```xml
<plugin>
  <groupId>org.openfolder</groupId>
  <artifactId>kotlin-asyncapi-maven-plugin</artifactId>
  <version>${kotlin-asyncapi.version}</version>
  <executions>
    <execution>
      <goals>
        <goal>generateResources</goal>
      </goals>
    </execution>
  </executions>
</plugin>
```

#### Embedded Scripting
If you can't use the Maven plugin for your project, you can also let your Spring Boot application evaluate the script at runtime.

You have to place your script in your `resources` folder. Similarly to the plugin, the `kotlin-asyncapi-spring-web` module will pick up the script and convert it to an `AsyncApiExtension`. By default, the library expects the script to be named `build.asyncapi.kts` and placed in the root of the `resources` folder. This can be changed in the application properties.

In order to enable embedded scripting, you need to make some additional configurations:
- add `kotlin-scripting-jvm-host` to the classpath
- unpack `kotlin-compiler-embeddable` from the Spring Boot executable JAR file

```xml
<dependency>
  <groupId>org.jetbrains.kotlin</groupId>
  <artifactId>kotlin-scripting-jvm-host</artifactId>
  <version>${kotlin.version}</version>
  <scope>runtime</scope>
</dependency>
```
```xml
<plugin>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-maven-plugin</artifactId>
  <configuration>
    <requiresUnpack>
      <dependency>
        <groupId>org.jetbrains.kotlin</groupId>
        <artifactId>kotlin-compiler-embeddable</artifactId>
      </dependency>
    </requiresUnpack>
  </configuration>
</plugin>
```

## Configuration
### <a name="spring-web-configuration"></a>Spring Web
You can configure the Spring Web integration in the application properties:

| Property                        | Description                                                   | Default                            |
|---------------------------------|---------------------------------------------------------------|------------------------------------|
| `asyncapi.enabled`              | Enables the autoconfiguration                                 | `true`                             |
| `asyncapi.path`                 | The resource path for serving the generated AsyncAPI document | `/docs/asyncapi`                   |
| `asyncapi.annotation.enabled`   | Enables the annotation scanning and processing                | `true`                             |
| `asyncapi.script.enabled`       | Enables the Kotlin script support                             | `true`                             |
| `asyncapi.script.resource-path` | Path to the generated script resource file                    | `asyncapi/generated/asyncapi.json` |
| `asyncapi.script.source-path`   | Path to the AsyncAPI Kotlin script file                       | `build.asyncapi.kts`               |

### <a name="ktor-configuration"></a>Ktor
You can configure the Ktor integration in the plugin configuration:

| Property          | Description                                                   | Default                            |
|-------------------|---------------------------------------------------------------|------------------------------------|
| `path`            | The resource path for serving the generated AsyncAPI document | `/docs/asyncapi`                   |
| `baseClass`       | The base class to filter code scanning packages               | `null`                             |
| `scanAnnotations` | Enables class path scanning for annotations                   | `true`                             |
| `extension`       | AsyncApiExtension hook                                        | `AsyncApiExtension.empty()`        |
| `extensions`      | For registering multiple AsyncApiExtension hooks              | `emptyList()`                      |
| `resourcePath`    | Path to the generated script resource file                    | `asyncapi/generated/asyncapi.json` |
| `sourcePath`      | Path to the AsyncAPI Kotlin script file                       | `build.asyncapi.kts`               |


### <a name="maven-plugin-configuration"></a>Maven Plugin
You can configure the plugin in the plugin configuration:

| Parameter          | Description                                           | Default               |
|--------------------|-------------------------------------------------------|-----------------------|
| `sourcePath`       | The relative path to the Kotlin script                | `build.asyncapi.kts`  |
| `targetPath`       | The relative path to the generated target resources   | `asyncapi/generated/` |
| `packageResources` | Adds the generated resources to the package classpath | `true`                |

## License
Kotlin AsyncAPI is Open Source software released under the
[Apache 2.0 license](http://www.apache.org/licenses/LICENSE-2.0.html).
