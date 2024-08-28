package com.asyncapi.kotlinasyncapi.mavenplugin

import org.apache.maven.model.Resource
import org.apache.maven.plugin.AbstractMojo
import org.apache.maven.plugins.annotations.LifecyclePhase
import org.apache.maven.plugins.annotations.Mojo
import org.apache.maven.plugins.annotations.Parameter
import org.apache.maven.project.MavenProject
import com.asyncapi.kotlinasyncapi.model.AsyncApi
import java.io.File
import javax.inject.Inject

@Mojo(
    name = "generateResources",
    defaultPhase = LifecyclePhase.GENERATE_RESOURCES,
    requiresProject = true
)
internal class AsyncApiPlugin @Inject constructor(
    private val scriptRunner: AsyncApiScriptRunner,
    private val fileWriter: AsyncApiFileWriter
) : AbstractMojo() {

    @Parameter(property = "sourcePath", defaultValue = "build.asyncapi.kts")
    lateinit var sourcePath: String

    @Parameter(property = "targetPath", defaultValue = "asyncapi/generated/")
    lateinit var targetPath: String

    @Parameter(property = "packageResources", defaultValue = "true")
    var packageResources: Boolean = true

    @Parameter(property = "project", defaultValue = "\${project}", readonly = true)
    lateinit var project: MavenProject

    private val targetFileName: String = "asyncapi.json"

    override fun execute() {
        val asyncApi = AsyncApi.asyncApi {
            info {
                title(project.name)
                version(project.version)
                description(project.description)
            }
            channels { }
        }
        scriptRunner.run(
            script = File(project.basedir, sourcePath),
            receiver = asyncApi
        )

        val targetDir = File(project.basedir, "target/$targetPath")
        log.info("Writing $targetFileName to $targetDir")
        fileWriter.write(
            asyncApi = asyncApi,
            file = File(targetDir, targetFileName)
        )

        if (packageResources) {
            val resource = Resource().also {
                it.directory = targetDir.path
                it.includes = listOf(targetFileName)
                it.targetPath = targetPath
            }

            log.info("Adding $targetDir to resources")
            project.addResource(resource)
        }
    }
}
