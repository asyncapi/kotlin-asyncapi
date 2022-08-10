package org.openfolder.kotlinasyncapi.springweb.service

import org.openfolder.kotlinasyncapi.model.AsyncApi
import org.openfolder.kotlinasyncapi.script.AsyncApiScript
import kotlin.script.experimental.api.SourceCode
import kotlin.script.experimental.api.implicitReceivers
import kotlin.script.experimental.jvm.dependenciesFromClassloader
import kotlin.script.experimental.jvm.jvm
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost

// TODO: Refactor cloning and context propagation
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
                        info = resource.info
                        channels = resource.channels
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
                                    dependenciesFromClassloader(
                                        "kotlin-stdlib",
                                        "kotlin-asyncapi-core",
                                        "kotlin-asyncapi-script",
                                        unpackJarCollections = true
                                    )
                                }
                            }
                        )
                    }
            }
    }
}
