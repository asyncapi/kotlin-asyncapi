package org.openfolder.kotlinasyncapi.mavenplugin

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import org.openfolder.kotlinasyncapi.model.AsyncApi
import java.io.File
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Named
internal class AsyncApiJsonWriter : AsyncApiFileWriter {

    private val objectMapper = ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)

    override fun write(asyncApi: AsyncApi, file: File) {
        file.parentFile?.mkdirs()
        file.createNewFile()

        objectMapper.writeValue(file, asyncApi)
    }
}
