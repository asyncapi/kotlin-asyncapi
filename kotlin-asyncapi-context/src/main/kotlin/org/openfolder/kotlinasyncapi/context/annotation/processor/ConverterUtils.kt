package org.openfolder.kotlinasyncapi.context.annotation.processor

import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.swagger.v3.core.converter.ModelConverters
import io.swagger.v3.core.jackson.ModelResolver

internal val MODEL_RESOLVER = ModelConverters().also {
    (it.converters.first() as ModelResolver).objectMapper().registerKotlinModule()
}
