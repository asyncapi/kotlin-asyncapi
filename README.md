![Logo](.github/assets/kotlin_asyncapi_logo.png)
[![Apache License 2](https://img.shields.io/badge/license-ASF2-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.txt)
[![Build status](https://github.com/OpenFolder/kotlin-asyncapi/actions/workflows/build.yml/badge.svg?branch=master)](https://github.com/OpenFolder/kotlin-asyncapi/actions/workflows/build.yml)
[![Qodana](https://github.com/OpenFolder/kotlin-asyncapi/actions/workflows/qodana.yml/badge.svg?branch=master)](https://openfolder.github.io/kotlin-asyncapi/qodana/report)
[![Maven Central status](https://img.shields.io/maven-central/v/org.openfolder/kotlin-asyncapi-parent.svg)](https://search.maven.org/#search%7Cga%7C1%7Corg.openfolder%20kotlin-asyncapi)

* [About](#about)
* [Module Roadmap](#module-roadmap)
* [Usage](#usage)
    * [Kotlin DSL](#kotlin-dsl-usage)
    * [Spring Web](#spring-web-usage)
    * [Kotlin Script](#kotlin-script-usage)
* [Configuration](#configuration)
    * [Spring Web](#spring-web-configuration)
    * [Maven Plugin](#maven-plugin-configuration)
* [License](#license)

## About
The Kotlin AsyncAPI project aims to provide convenience tools for generating and serving 
[AsyncAPI](https://www.asyncapi.com/) documentation. The core of this project is a 
[Kotlin DSL](https://kotlinlang.org/docs/type-safe-builders.html) for building the specification in a typesafe way. 
The modules around that core build a framework for documenting asynchronous microservice APIs.

## Module Roadmap
| Module                  | Description                                                                    | State              |
|-------------------------|--------------------------------------------------------------------------------|--------------------|
| **core**                | Kotlin DSL for building AsyncAPI specifications                                | :white_check_mark: |
| **spring&#x2011;web**   | Spring Boot autoconfiguration for serving the generated document               | :white_check_mark: |
| **script**              | Kotlin scripting support for configuration as code                             | :white_check_mark: |
| **maven&#x2011;plugin** | Maven plugin for evaluating AsyncAPI scripts and packaging generated resources | :white_check_mark: |
| **annotation**          | Technology agnostic annotations for meta-configuration                         | :soon:             |
| **template**            | Template engine for reusing similar AsyncAPI components                        | :eyes:             |

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
- enable the autoconfiguration by annotating a configuration class with `@EnableAsyncApi`
- [optional] provide `AsyncApiExtension` beans for the application context
- [optional] add a Kotlin build script to the classpath -> see [Kotlin script usage](#kotlin-script-usage)

The library registers a default `info` extension using the `Implementation-Title` and `Implementation-Version` of the `MANIFEST.md` file. Please note that this information is only available if the application was started from a JAR file. The default info will **not** work if you start the application from your IDE.

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
```
```xml
<dependency>
  <groupId>org.openfolder</groupId>
  <artifactId>kotlin-asyncapi-spring-web</artifactId>
  <version>${kotlin-asyncapi.version}</version>
</dependency>
```

### <a name="kotlin-script-usage"></a>Kotlin Script
[Kotlin Scripting](https://github.com/Kotlin/KEEP/blob/b0c8a37db684eaf74bb1305f3c180b5d2537d787/proposals/scripting-support.md) allows us to execute a piece of code in a provided context. The IDE can still provide features like autocompletion and syntax highlighting. Furthermore, it provides the following benefits:
- separate AsyncAPI documentation from application source code
- focus on AsyncAPI content and don't worry about the build context or spring web integration
- use AsyncAPI Kotlin DSL in Java projects

You have two options to use Kotlin scripting in your project:
- let your Spring Boot application evaluate the script at runtime
- let the Maven plugin evaluate the script during build time

#### Embedded Scripting
Your Spring Boot application can pick up your build script from the classpath and convert it to an `AsyncApiExtension`. You just have to place your script with the file extension `asyncapi.kts` in your `resources` folder. By default, the library expects the script to be named `build.asyncapi.kts` and placed in the root of the `resources` folder. This can be changed in the application properties.

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
Embedded scripting is enabled by default for Spring Boot applications. If you don't want to include the Kotlin compiler in your build, you can also evaluate the script with the Maven plugin and use the packaged script resources in your Spring Boot application. 

You just need to exclude the `kotlin-scripting-jvm-host` dependency from the `kotlin-asyncapi-spring-web` artifact. The Spring Web integration automatically picks up the script resource from the `resource-path` and converts it to an `AsyncApiExtension`.

#### Maven Plugin
The Maven plugin will evaluate the script and put the generated AsyncAPI JSON on the package classpath. Your application can convert the resource to an AsyncApi model object. 

By default, the plugin expects the script to be named `build.asyncapi.kts` and placed in the project root. The script path and resource target path can be changed in the plugin configuration.

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

## Configuration
### <a name="spring-web-configuration"></a>Spring Web
You can configure the Spring Web integration in the application properties:

| Property                        | Description                                                   | Default                                      |
|---------------------------------|---------------------------------------------------------------|----------------------------------------------|
| `asyncapi.enabled`              | Enables the autoconfiguration                                 | `true`                                       |
| `asyncapi.path`                 | The resource path for serving the generated AsyncAPI document | `/docs/asyncapi`                             |
| `asyncapi.script.enabled`       | Enables the Kotlin script support                             | `true`                                       |
| `asyncapi.script.resource-path` | Path to the generated script resource file                    | `classpath:asyncapi/generated/asyncapi.json` |
| `asyncapi.script.source-path`   | Path to the AsyncAPI Kotlin script file                       | `classpath:build.asyncapi.kts`               |

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
