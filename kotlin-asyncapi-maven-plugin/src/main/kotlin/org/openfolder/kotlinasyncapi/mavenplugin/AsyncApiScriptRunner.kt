package org.openfolder.kotlinasyncapi.mavenplugin

import org.openfolder.kotlinasyncapi.model.AsyncApi
import org.openfolder.kotlinasyncapi.script.AsyncApiScript
import java.io.File
import javax.inject.Named
import javax.inject.Singleton
import kotlin.script.experimental.api.implicitReceivers
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost

internal interface AsyncApiScriptRunner {
    fun run(script: File): AsyncApi
}

@Singleton
@Named
internal class AsyncApiScriptHost : AsyncApiScriptRunner {

    private val jvmScriptingHost = BasicJvmScriptingHost()

    override fun run(script: File): AsyncApi {
        val scriptReceiver = AsyncApi()

        jvmScriptingHost.evalWithTemplate<AsyncApiScript>(
            script = script.toScriptSource(),
            evaluation = {
                implicitReceivers(scriptReceiver)
            }
        )

        return scriptReceiver
    }
}
