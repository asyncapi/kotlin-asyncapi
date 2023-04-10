package org.openfolder.kotlinasyncapi.springweb.context

import org.openfolder.kotlinasyncapi.model.AsyncApi
import org.openfolder.kotlinasyncapi.springweb.EnableAsyncApi
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
internal class PackageInfoProvider(
    context: ApplicationContext
) : AsyncApiContextProvider {

    private val applicationPackage = context
        .getBeansWithAnnotation(EnableAsyncApi::class.java)
        .values
        .firstOrNull()
        ?.javaClass
        ?.`package`

    override val asyncApi: AsyncApi? by lazy {
        AsyncApi().apply {
            info {
                title(applicationPackage?.implementationTitle ?: "AsyncAPI Definition")
                version(applicationPackage?.implementationVersion ?: "SNAPSHOT")
            }
        }
    }
}
