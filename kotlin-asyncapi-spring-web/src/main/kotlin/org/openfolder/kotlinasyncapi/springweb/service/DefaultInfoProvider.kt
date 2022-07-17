package org.openfolder.kotlinasyncapi.springweb.service

import org.openfolder.kotlinasyncapi.springweb.EnableAsyncApi
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Service

@Service
internal class DefaultInfoProvider(
    context: ApplicationContext
) {
    private val applicationPackage = context
        .getBeansWithAnnotation(EnableAsyncApi::class.java)
        .values
        .firstOrNull()
        ?.javaClass
        ?.`package`

    val title by lazy { applicationPackage?.implementationTitle }

    val version by lazy { applicationPackage?.implementationVersion }
}
