package org.openfolder.kotlinasyncapi.script

import org.openfolder.kotlinasyncapi.model.AsyncApi
import kotlin.script.experimental.annotations.KotlinScript
import kotlin.script.experimental.api.ScriptAcceptedLocation
import kotlin.script.experimental.api.ScriptCompilationConfiguration
import kotlin.script.experimental.api.acceptedLocations
import kotlin.script.experimental.api.defaultImports
import kotlin.script.experimental.api.ide
import kotlin.script.experimental.api.implicitReceivers
import kotlin.script.experimental.jvm.dependenciesFromClassContext
import kotlin.script.experimental.jvm.jvm

@KotlinScript(
    displayName = "AsyncApi build script",
    fileExtension = "asyncapi.kts",
    compilationConfiguration = AsyncApiScriptCompilationConfiguration::class
)
abstract class AsyncApiScript

object AsyncApiScriptCompilationConfiguration : ScriptCompilationConfiguration({
    defaultImports("org.openfolder.kotlinasyncapi.model.*")
    implicitReceivers(AsyncApi::class)
    ide {
        acceptedLocations(ScriptAcceptedLocation.Everywhere)
    }
    jvm {
        dependenciesFromClassContext(
            AsyncApiScript::class,
            "kotlin-stdlib",
            "kotlin-asyncapi-core",
            "kotlin-asyncapi-script",
            unpackJarCollections = true
        )
    }
})
