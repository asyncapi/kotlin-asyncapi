package org.openfolder.kotlinasyncapi.mavenplugin

import org.apache.maven.model.Resource
import org.apache.maven.plugin.AbstractMojo
import org.apache.maven.plugins.annotations.LifecyclePhase
import org.apache.maven.plugins.annotations.Mojo
import org.apache.maven.plugins.annotations.Parameter
import org.apache.maven.project.MavenProject
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
    private lateinit var sourcePath: String

    @Parameter(property = "targetPath", defaultValue = "asyncapi/generated/")
    private lateinit var targetPath: String

    @Parameter(property = "packageResources", defaultValue = "true")
    private var packageResources: Boolean = true

    @Parameter(property = "project", defaultValue = "\${project}", readonly = true)
    private lateinit var project: MavenProject

    override fun execute() {
        val asyncApi = scriptRunner.run(File(sourcePath))
        val targetFileName = "asyncapi.json"
        val fullTargetPath = "./target/$targetPath".apply {
            if (last() != '/') plus('/')
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
