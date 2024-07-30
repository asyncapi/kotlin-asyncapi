package org.openfolder.kotlinasyncapi.ktor

import io.ktor.server.application.*

val AsyncApiPlugin = createApplicationPlugin(
    name = "AsyncApiPlugin",
    createConfiguration = ::AsyncApiConfiguration
) {
    AsyncApiModule(
        environment = application.environment,
        configuration = pluginConfig,
    ).apply { application.asyncApiModule() }
}
