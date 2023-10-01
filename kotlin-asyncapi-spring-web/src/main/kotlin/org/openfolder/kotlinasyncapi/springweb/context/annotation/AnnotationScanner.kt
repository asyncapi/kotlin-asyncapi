package org.openfolder.kotlinasyncapi.springweb.context.annotation

import io.github.classgraph.ClassGraph
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

internal interface AnnotationScanner {
    fun scan(scanPackage: String, annotation: KClass<out Annotation>): List<KClass<*>>
}

@Component
internal class DefaultAnnotationScanner : AnnotationScanner {
    override fun scan(scanPackage: String, annotation: KClass<out Annotation>): List<KClass<*>> {
        val packageClasses = ClassGraph()
            .enableAllInfo()
            .acceptPackages(scanPackage)
            .scan()

        return packageClasses.getClassesWithAnnotation(annotation.java).standardClasses.map {
            it.loadClass().kotlin
        }
    }
}
