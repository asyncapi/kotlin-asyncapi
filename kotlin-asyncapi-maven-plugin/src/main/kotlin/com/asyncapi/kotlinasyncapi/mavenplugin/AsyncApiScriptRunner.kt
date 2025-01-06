package com.asyncapi.kotlinasyncapi.mavenplugin

import com.asyncapi.kotlinasyncapi.model.AsyncApi
import com.asyncapi.kotlinasyncapi.script.AsyncApiScript
import java.io.File
import javax.inject.Named
import javax.inject.Singleton
import kotlin.script.experimental.api.implicitReceivers
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost

internal interface AsyncApiScriptRunner {
    fun run(script: File, receiver: AsyncApi = AsyncApi()): AsyncApi
}

@Singleton
@Named
internal class AsyncApiScriptHost : AsyncApiScriptRunner {

    private val jvmScriptingHost = BasicJvmScriptingHost()

    override fun run(script: File, receiver: AsyncApi) =
        receiver.also {
            jvmScriptingHost.evalWithTemplate<AsyncApiScript>(
                script = script.toScriptSource(),
                evaluation = {
                    implicitReceivers(it)
                }
            )
        }
}
