package com.asyncapi.kotlinasyncapi.ktor

import io.ktor.server.application.createApplicationPlugin

val AsyncApiPlugin = createApplicationPlugin(
    name = "AsyncApiPlugin",
    createConfiguration = ::AsyncApiConfiguration
) {
    AsyncApiModule(
        environment = application.environment,
        configuration = pluginConfig,
    ).apply { application.asyncApiModule() }
}
