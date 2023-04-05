# Architecture
This project is organized as a monorepo with the following Maven modules:
- `kotlin-asyncapi-core`
- `kotlin-asyncapi-maven-plugin`
- `kotlin-asyncapi-script`
- `kotlin-asyncapi-spring-web`

<img align="right" width="400" alt="eNrFU0FyhCAQvOcVfEBvuXDIXxBGd0oEC9DNJuXfA4LKGqpM7SWWljLMdI9NT6-dRFUx-1CcjUjJ9xvxF9cG_Ld1DxnfRvdQCWZvlLwvy5ozsBlUNcqpQ3WVa7nB0V1mjQZVV92h2frwsXJB2uVa-biUYLZ0MDNySKvR6BkFGJvW8OlAWdQqBgJKePy9CVANWoA8xQS0qND5urjRR9G4HkaM1EdwK6qDhKT6OCFT0kwohf1F-ZR6ENLwR8gkfoElThd4orSh_NQWJSuMr4sRFvCCYi12k4mrVpsCZH6yGfCJkno5gU_OE-w7vbPprF-AjabjBliOmYlB7uhuyUM1Uzj4REGJMxMsJWV2O_0_2_OwrD4UZdbYkSDN4xXuOg1A8diOrGNuVoPNmQIk2rHsjAxin60LqtQQ3YbxIAq21AqU98wl2zG5f6WLGp9_S7ee5wdVmcOv" src="./architecture.svg">

## `core`
The core module provides the Kotlin DSL for building the AsyncAPI POJO. This POJO model can be serialized to the AsyncAPI definition JSON.

Every module in this project depends on the core. The core module itself is independent of the rest of the project and does not depend on any other module.

## `script`
The script module configures the context for the `asyncapi.kts` script and defines compilation properties for the Kotlin compiler. It tells the compiler what the script environment should look like.

## `maven-plugin`
The maven plugin executes the `asyncapi.kts` script and adds the resulting AsyncAPI definition JSON file to the project resources. This file can be picked up and extended by the `spring-web` module.

## `spring-web`
The spring-web module glues everything together to build and serve the AsyncAPI definition for a Spring Boot microservice. 

### `controller`
The controller provides an endpoint that returns the cached AsyncAPI definition.

### `service`
The service creates an empty AsyncAPI model and uses the registered extensions to extend it.

### `extensions`
The extensions add or override properties of an AsyncAPI model.

### `providers`
The providers provide AsyncAPI data from the application context (e.g. default values). The data can be used to extend an AsyncAPI model.
