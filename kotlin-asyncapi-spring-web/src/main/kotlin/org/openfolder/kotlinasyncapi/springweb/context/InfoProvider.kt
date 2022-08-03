package org.openfolder.kotlinasyncapi.springweb.context

import org.openfolder.kotlinasyncapi.springweb.EnableAsyncApi
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

internal interface InfoProvider {

    val title: String?

    val version: String?
}

@Component
internal class DefaultInfoProvider(
    context: ApplicationContext
) : InfoProvider {

    private val applicationPackage = context
        .getBeansWithAnnotation(EnableAsyncApi::class.java)
        .values
        .firstOrNull()
        ?.javaClass
        ?.`package`

    override val title by lazy { applicationPackage?.implementationTitle }

    override val version by lazy { applicationPackage?.implementationVersion }
}
