# Architecture
This project is organized as a monorepo with the following Maven modules:
- `kotlin-asyncapi-core`
- `kotlin-asyncapi-maven-plugin`
- `kotlin-asyncapi-script`
- `kotlin-asyncapi-spring-web`

<img align="right" width="400" src="https://kroki.io/d2/svg/eNrFVNtygyAQffcr-AElTtq08aH_QmCjOyIwgLZpx38vSC7GZJr2qU9yWc_Z3XOWVnuJKmfuoDgzWJGvjBCuLYSV8weZvla3kAvmmoo8j2OI6NgAKjeyr1H9HOm4ReMfxBiLqs7fYZf4w8n94OmOaxVOpQSbQsEOyGFaG6sHFGDdtIMPD8qhVnE7ZmOWncrMOy1AJjLXMBOosGN1BMEAX5HGe-MqSrFoD13OhSq47qhptNeOTqGOaouheibpalXSl9Ur3W5KuoWyMKq-IhOwR4UeI_BDRjYwz6wravRNv-tDdbFeUH7KoKfl5mlVrtdPkaFN4oULg7Edp4MTcxGFJPkbWda961EKt2jHVeA855ADMomf4IjXNxxJ4PjzIp2KTCDhr3TCIlpUb491b9Nur-0N4NxbM9gFYRXkBd77AH--ab07-u3PoMny3AKbI87aQN6DIEcXF0wF8TyIinjbw3jbk7Oh_5vrelCniRD3OVM-guwOf2cujkN4V65L1GVyJ1MNs-pJsuA9P8wAzvP9gOiYTnV6EC400YpahXFyj7gur8dvyVJ3lyXpffYNJkHzdA==">

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