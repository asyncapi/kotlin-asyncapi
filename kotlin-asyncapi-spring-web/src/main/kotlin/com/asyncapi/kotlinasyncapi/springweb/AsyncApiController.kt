package com.asyncapi.kotlinasyncapi.springweb

import com.asyncapi.kotlinasyncapi.context.service.AsyncApiSerializer
import com.asyncapi.kotlinasyncapi.context.service.AsyncApiService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${asyncapi.path:/docs/asyncapi}")
internal class AsyncApiController(
    private val service: AsyncApiService,
    private val serializer: AsyncApiSerializer
) {

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun asyncApi() = with(serializer) {
        service.asyncApi().serialize()
    }
}
