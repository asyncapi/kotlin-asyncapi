package org.openfolder.kotlinasyncapi.mavenplugin

import org.openfolder.kotlinasyncapi.model.AsyncApi
import java.io.File

internal interface AsyncApiFileWriter {
    fun write(asyncApi: AsyncApi, file: File)
}
