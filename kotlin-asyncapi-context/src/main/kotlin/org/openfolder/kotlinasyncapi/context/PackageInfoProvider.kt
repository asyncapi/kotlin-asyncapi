package org.openfolder.kotlinasyncapi.context

import org.openfolder.kotlinasyncapi.model.AsyncApi

class PackageInfoProvider(
    private val applicationPackage: Package?
) : AsyncApiContextProvider {

    override val asyncApi: AsyncApi? by lazy {
        AsyncApi().apply {
            info {
                title(applicationPackage?.implementationTitle ?: "AsyncAPI Definition")
                version(applicationPackage?.implementationVersion ?: "SNAPSHOT")
            }
        }
    }
}
