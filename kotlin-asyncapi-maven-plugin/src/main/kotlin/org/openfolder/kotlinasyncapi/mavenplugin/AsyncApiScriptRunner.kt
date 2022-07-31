package org.openfolder.kotlinasyncapi.mavenplugin

import org.openfolder.kotlinasyncapi.model.AsyncApi
import java.io.File

internal interface AsyncApiScriptRunner {
    fun run(script: File): AsyncApi
}
