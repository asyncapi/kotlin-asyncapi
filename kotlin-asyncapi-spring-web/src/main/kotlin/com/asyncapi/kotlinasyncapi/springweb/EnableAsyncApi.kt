package com.asyncapi.kotlinasyncapi.springweb

import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS)
@MustBeDocumented
@Import(AsyncApiMarkerConfiguration::class)
annotation class EnableAsyncApi
