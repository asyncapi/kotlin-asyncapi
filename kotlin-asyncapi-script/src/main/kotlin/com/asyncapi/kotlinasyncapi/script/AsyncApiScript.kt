package com.asyncapi.kotlinasyncapi.script

import com.asyncapi.kotlinasyncapi.model.AsyncApi
import kotlin.script.experimental.annotations.KotlinScript
import kotlin.script.experimental.api.ScriptAcceptedLocation
import kotlin.script.experimental.api.ScriptCompilationConfiguration
import kotlin.script.experimental.api.acceptedLocations
import kotlin.script.experimental.api.defaultImports
import kotlin.script.experimental.api.ide
import kotlin.script.experimental.api.implicitReceivers
import kotlin.script.experimental.jvm.jvm
import kotlin.script.experimental.jvm.updateClasspath
import kotlin.script.experimental.jvm.util.scriptCompilationClasspathFromContextOrNull

@KotlinScript(
    displayName = "AsyncApi build script",
    fileExtension = "asyncapi.kts",
    compilationConfiguration = AsyncApiScriptCompilationConfiguration::class
)
abstract class AsyncApiScript

internal object AsyncApiScriptCompilationConfiguration : ScriptCompilationConfiguration({
    defaultImports("com.asyncapi.kotlinasyncapi.model.*")
    implicitReceivers(AsyncApi::class)
    ide {
        acceptedLocations(ScriptAcceptedLocation.Everywhere)
    }
    jvm {
        updateClasspath(
            classpath = scriptCompilationClasspathFromContextOrNull(
                "kotlin-stdlib",
                "kotlin-asyncapi-core",
                "kotlin-asyncapi-script",
                classLoader = AsyncApiScript::class.java.classLoader
            )
        )
    }
})
