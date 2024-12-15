package com.asyncapi.kotlinasyncapi.context.annotation

import io.github.classgraph.ClassGraph
import kotlin.reflect.KClass

interface AnnotationScanner {
    fun scan(classLoader: ClassLoader? = null, scanPackage: String? = null, annotation: KClass<out Annotation>): List<KClass<*>>
}

class DefaultAnnotationScanner : AnnotationScanner {
    override fun scan(classLoader: ClassLoader?, scanPackage: String?, annotation: KClass<out Annotation>): List<KClass<*>> {
        val packageClasses = ClassGraph()
            .enableAllInfo()
            .apply {
                if (classLoader != null) {
                    addClassLoader(classLoader)
                }
                if (scanPackage != null) {
                    acceptPackages(scanPackage)
                }
            }
            .scan()

        return packageClasses.getClassesWithAnnotation(annotation.java).standardClasses.map {
            it.loadClass().kotlin
        }
    }
}
