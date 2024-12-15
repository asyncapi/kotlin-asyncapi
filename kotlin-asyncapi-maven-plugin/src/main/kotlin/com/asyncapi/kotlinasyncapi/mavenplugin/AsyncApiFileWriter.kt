package com.asyncapi.kotlinasyncapi.mavenplugin

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.asyncapi.kotlinasyncapi.model.AsyncApi
import java.io.File
import javax.inject.Named
import javax.inject.Singleton

internal interface AsyncApiFileWriter {
    fun write(asyncApi: AsyncApi, file: File)
}

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
