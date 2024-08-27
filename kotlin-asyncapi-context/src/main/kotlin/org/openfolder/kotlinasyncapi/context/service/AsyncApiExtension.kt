package org.openfolder.kotlinasyncapi.context.service

import org.openfolder.kotlinasyncapi.model.AsyncApi
import org.openfolder.kotlinasyncapi.script.AsyncApiScript
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
            object : AsyncApiExtension {
                override val order = order
                override fun extend(asyncApi: AsyncApi) =
                    asyncApi.apply {
                        try {
                            resource.info.let { info = it }
                        } catch (_: UninitializedPropertyAccessException) { }
                        try {
                            resource.channels.let { channels = it }
                        } catch (_: UninitializedPropertyAccessException) { }
                        resource.id?.let { id = it }
                        resource.servers?.let { servers = it }
                        resource.defaultContentType?.let { defaultContentType = it }
                        resource.components?.let { components = it }
                        resource.tags?.let { tags = it }
                        resource.externalDocs?.let { externalDocs = it }
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
