package com.asyncapi.kotlinasyncapi.context.service

import com.asyncapi.kotlinasyncapi.model.AsyncApi
import com.asyncapi.kotlinasyncapi.script.AsyncApiScript
import kotlin.script.experimental.api.SourceCode
import kotlin.script.experimental.api.implicitReceivers
import kotlin.script.experimental.jvm.jvm
import kotlin.script.experimental.jvm.updateClasspath
import kotlin.script.experimental.jvm.util.scriptCompilationClasspathFromContextOrNull
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost

interface AsyncApiExtension {

    /**
     * Extensions with higher values overwrite extensions with lower values.
     */
    val order: Int

    /**
     * Extends the generated AsyncApi resource
     */
    fun extend(asyncApi: AsyncApi): AsyncApi

    companion object {
        fun builder(order: Int = 0, build: AsyncApi.() -> Unit): AsyncApiExtension =
            object : AsyncApiExtension {
                override val order = order
                override fun extend(asyncApi: AsyncApi) = asyncApi.apply(build)
            }

        fun from(order: Int = 0, resource: AsyncApi) =
            from(order = order) { resource }

        fun from(order: Int = 0, resource: () -> AsyncApi) =
            object : AsyncApiExtension {
                override val order = order
                override fun extend(asyncApi: AsyncApi) =
                    asyncApi.apply {
                        val resolvedResource = resource()
                        try {
                            resolvedResource.info.let { info = it }
                        } catch (_: UninitializedPropertyAccessException) { }
                        try {
                            resolvedResource.channels.let { channels = it }
                        } catch (_: UninitializedPropertyAccessException) { }
                        resolvedResource.id?.let { id = it }
                        resolvedResource.servers?.let { servers = it }
                        resolvedResource.defaultContentType?.let { defaultContentType = it }
                        resolvedResource.components?.let { components = it }
                        resolvedResource.tags?.let { tags = it }
                        resolvedResource.externalDocs?.let { externalDocs = it }
                    }
            }

        fun from(order: Int = 0, script: SourceCode) =
            object : AsyncApiExtension {
                private val jvmScriptingHost = BasicJvmScriptingHost()

                override val order = order
                override fun extend(asyncApi: AsyncApi) =
                    asyncApi.apply {
                        jvmScriptingHost.evalWithTemplate<AsyncApiScript>(
                            script = script,
                            evaluation = {
                                implicitReceivers(asyncApi)
                            },
                            compilation = {
                                jvm {
                                    updateClasspath(
                                        classpath = scriptCompilationClasspathFromContextOrNull(
                                            "kotlin-stdlib",
                                            "kotlin-asyncapi-core",
                                            "kotlin-asyncapi-script",
                                            unpackJarCollections = true
                                        )
                                    )
                                }
                            }
                        )
                    }
            }

        fun empty(): AsyncApiExtension =
            object : AsyncApiExtension {
                override val order = 0
                override fun extend(asyncApi: AsyncApi) = asyncApi
            }
    }
}
