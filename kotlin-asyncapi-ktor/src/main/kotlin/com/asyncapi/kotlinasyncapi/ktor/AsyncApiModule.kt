package com.asyncapi.kotlinasyncapi.ktor

import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationEnvironment
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlin.script.experimental.host.toScriptSource
import com.asyncapi.kotlinasyncapi.context.PackageInfoProvider
import com.asyncapi.kotlinasyncapi.context.ResourceProvider
import com.asyncapi.kotlinasyncapi.context.annotation.AnnotationProvider
import com.asyncapi.kotlinasyncapi.context.annotation.DefaultAnnotationScanner
import com.asyncapi.kotlinasyncapi.context.annotation.processor.AsyncApiComponentProcessor
import com.asyncapi.kotlinasyncapi.context.annotation.processor.ChannelProcessor
import com.asyncapi.kotlinasyncapi.context.annotation.processor.MessageProcessor
import com.asyncapi.kotlinasyncapi.context.annotation.processor.SchemaProcessor
import com.asyncapi.kotlinasyncapi.context.service.AsyncApiExtension
import com.asyncapi.kotlinasyncapi.context.service.AsyncApiSerializer
import com.asyncapi.kotlinasyncapi.context.service.AsyncApiService
import com.asyncapi.kotlinasyncapi.context.service.DefaultAsyncApiSerializer
import com.asyncapi.kotlinasyncapi.context.service.DefaultAsyncApiService
import com.asyncapi.kotlinasyncapi.model.AsyncApi

class AsyncApiModule(
    environment: ApplicationEnvironment,
    private val configuration: AsyncApiConfiguration,
) {
    private val packageInfoProvider = with(configuration) {
        PackageInfoProvider(applicationPackage = baseClass?.java?.`package`)
    }

    private val asyncApiPackageInfoExtension =
        packageInfoProvider.asyncApi?.let { AsyncApiExtension.from(order = -100, it) }

    private val packageResourceProvider = with(configuration) {
        ResourceProvider(path = resourcePath)
    }

    private val asyncApiPackageResourceExtension =
        packageResourceProvider.asyncApi?.let { AsyncApiExtension.from(order = -10, resource = it) }

    private val asyncApiDefaultChannelsExtension =
        AsyncApiExtension.builder(order = -100) {
            channels { }
        }

    private val messageProcessor = MessageProcessor()

    private val schemaProcessor = SchemaProcessor()

    private val channelProcessor = ChannelProcessor()

    private val asyncApiComponentProcessor = AsyncApiComponentProcessor()

    private val annotationScanner = DefaultAnnotationScanner()

    private val annotationProvider = with(configuration) {
        AnnotationProvider(
            applicationPackage = baseClass?.java?.`package`,
            classLoader = environment.classLoader,
            scanner = annotationScanner,
            messageProcessor = messageProcessor,
            schemaProcessor = schemaProcessor,
            channelProcessor = channelProcessor,
            asyncApiComponentProcessor = asyncApiComponentProcessor
        )
    }

    private val asyncApiAnnotationExtension: AsyncApiExtension? =
        object : AsyncApiExtension {
            override val order: Int = -1

            override fun extend(asyncApi: AsyncApi): AsyncApi {
                val annotated = annotationProvider.asyncApi ?: return asyncApi
                return AsyncApiExtension.from(order = order, resource = annotated).extend(asyncApi)
            }
        }

    private val scriptResourceProvider =
        runCatching {
            Class.forName(
                "kotlin.script.experimental.jvmhost.BasicJvmScriptingHost",
                false,
                environment.classLoader
            )
        }.getOrNull()?.let {
            with(configuration) {
                ResourceProvider(path = sourcePath)
            }
        }

    private val asyncApiScriptResourceExtension = scriptResourceProvider?.resource?.let {
        AsyncApiExtension.from(order = -1, script = it.toScriptSource())
    }

    val asyncApiExtensions = listOfNotNull(
        configuration.extension,
        *configuration.extensions.toTypedArray(),
        asyncApiPackageInfoExtension,
        asyncApiPackageResourceExtension,
        asyncApiDefaultChannelsExtension,
        asyncApiAnnotationExtension,
        asyncApiScriptResourceExtension,
    )

    val asyncApiService: AsyncApiService = DefaultAsyncApiService(asyncApiExtensions)

    val asyncApiSerializer: AsyncApiSerializer = DefaultAsyncApiSerializer()

    fun Application.asyncApiModule() = routing {
        route(configuration.path) {
            get {
                with(asyncApiSerializer) {
                    val asyncApi = asyncApiService.asyncApi().serialize()
                    call.respondText(
                        text = asyncApi,
                        status = HttpStatusCode.OK,
                        contentType = ContentType.Application.Json,
                    )
                }
            }
        }
    }
}
