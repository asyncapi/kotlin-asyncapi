package org.openfolder.kotlinasyncapi.springweb

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS)
@MustBeDocumented
@Import(AsyncApiMarkerConfiguration::class)
@ConditionalOnProperty(name = ["asyncapi.enabled"], havingValue = "true", matchIfMissing = true)
annotation class EnableAsyncApi
