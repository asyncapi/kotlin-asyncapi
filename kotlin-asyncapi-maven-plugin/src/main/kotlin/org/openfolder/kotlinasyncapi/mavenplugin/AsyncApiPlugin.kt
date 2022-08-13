package org.openfolder.kotlinasyncapi.mavenplugin

import org.apache.maven.model.Resource
import org.apache.maven.plugin.AbstractMojo
import org.apache.maven.plugins.annotations.LifecyclePhase
import org.apache.maven.plugins.annotations.Mojo
import org.apache.maven.plugins.annotations.Parameter
import org.apache.maven.project.MavenProject
import org.openfolder.kotlinasyncapi.model.AsyncApi
import java.io.File
import javax.inject.Inject

@Mojo(
    name = "generateResources",
    defaultPhase = LifecyclePhase.GENERATE_RESOURCES,
    requiresProject = true
)
internal class AsyncApiPlugin @Inject constructor(
    private val scriptRunner: AsyncApiScriptRunner,
    private val fileWriter: AsyncApiFileWriter,
    private val targetFileName: String = "asyncapi.json"
) : AbstractMojo() {

    @Parameter(property = "sourcePath", defaultValue = "build.asyncapi.kts")
    lateinit var sourcePath: String

    @Parameter(property = "targetPath", defaultValue = "asyncapi/generated/")
    lateinit var targetPath: String

    @Parameter(property = "packageResources", defaultValue = "true")
    var packageResources: Boolean = true

    @Parameter(property = "project", defaultValue = "\${project}", readonly = true)
    lateinit var project: MavenProject

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
            script = File(sourcePath),
            receiver = asyncApi
        )

        val fullTargetPath = "./target/$targetPath".let {
            if (it.last() != '/') it.plus('/')
            else it
        }
        log.info("Writing $targetFileName to $fullTargetPath")
        fileWriter.write(
            asyncApi = asyncApi,
            file = File(fullTargetPath + targetFileName)
        )

        if (packageResources) {
            val resource = Resource().also {
                it.directory = fullTargetPath
                it.includes = listOf(targetFileName)
                it.targetPath = targetPath
            }

            log.info("Adding $fullTargetPath to resources")
            project.addResource(resource)
        }
    }
}
