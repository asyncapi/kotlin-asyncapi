package org.openfolder.kotlinasyncapi.ktor

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.openfolder.kotlinasyncapi.context.PackageInfoProvider
import org.openfolder.kotlinasyncapi.context.PackageResourceProvider
import org.openfolder.kotlinasyncapi.context.annotation.AnnotationProvider
import org.openfolder.kotlinasyncapi.context.annotation.DefaultAnnotationScanner
import org.openfolder.kotlinasyncapi.context.annotation.processor.ChannelProcessor
import org.openfolder.kotlinasyncapi.context.annotation.processor.MessageProcessor
import org.openfolder.kotlinasyncapi.context.annotation.processor.SchemaProcessor
import org.openfolder.kotlinasyncapi.context.service.AsyncApiExtension
import org.openfolder.kotlinasyncapi.context.service.AsyncApiSerializer
import org.openfolder.kotlinasyncapi.context.service.AsyncApiService
import org.openfolder.kotlinasyncapi.context.service.DefaultAsyncApiSerializer
import org.openfolder.kotlinasyncapi.context.service.DefaultAsyncApiService

class AsyncApiModule(
    environment: ApplicationEnvironment,
    private val configuration: AsyncApiConfiguration,
) {
    private val packageInfoProvider = with(configuration) {
        PackageInfoProvider(applicationPackage = baseClass?.java?.`package`)
    }

    private val asyncApiPackageInfoExtension =
        packageInfoProvider.asyncApi?.let { AsyncApiExtension.from(order = -10, it) }

    private val packageResourceProvider = with(configuration) {
        PackageResourceProvider(path = resourcePath)
    }

    private val asyncApiPackageResourceExtension =
        packageResourceProvider.asyncApi?.let { AsyncApiExtension.from(resource = it) }

    private val asyncApiDefaultChannelsExtension =
        AsyncApiExtension.builder(order = -10) {
            channels { }
        }

    private val messageProcessor = MessageProcessor()

    private val schemaProcessor = SchemaProcessor()

    private val channelProcessor = ChannelProcessor()

    private val annotationScanner = DefaultAnnotationScanner()

    private val annotationProvider = with(configuration) {
        AnnotationProvider(
            applicationPackage = baseClass?.java?.`package`,
            classLoader = environment.classLoader,
            scanner = annotationScanner,
            messageProcessor = messageProcessor,
            schemaProcessor = schemaProcessor,
            channelProcessor = channelProcessor,
        )
    }

    private val asyncApiAnnotationExtension =
        annotationProvider.asyncApi?.let { AsyncApiExtension.from(order = -1, it) }

    val asyncApiExtensions = listOfNotNull(
        configuration.extension,
        *configuration.extensions.toTypedArray(),
        asyncApiPackageInfoExtension,
        asyncApiPackageResourceExtension,
        asyncApiDefaultChannelsExtension,
        asyncApiAnnotationExtension,
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
