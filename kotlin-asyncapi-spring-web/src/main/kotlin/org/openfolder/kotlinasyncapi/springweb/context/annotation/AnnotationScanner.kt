package org.openfolder.kotlinasyncapi.springweb.context.annotation

import org.openfolder.kotlinasyncapi.annotation.AsyncApiAnnotation
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
import org.springframework.core.type.filter.AnnotationTypeFilter
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

internal interface AnnotationScanner {
    fun scan(scanPackage: String, annotation: KClass<out Annotation>): List<KClass<*>>
}

@Component
internal class DefaultAnnotationScanner(
    private val context: ApplicationContext
): AnnotationScanner {
    override fun scan(scanPackage: String, annotation: KClass<out Annotation>): List<KClass<*>> {
        val classPathScanner = ClassPathScanningCandidateComponentProvider(false).also {
            it.addIncludeFilter(AnnotationTypeFilter(AsyncApiAnnotation::class.java))
        }

        return classPathScanner.findCandidateComponents(scanPackage).map {
            Class.forName(it.beanClassName).kotlin
        }.toList()
    }
}
