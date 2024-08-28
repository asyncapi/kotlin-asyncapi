package com.asyncapi.kotlinasyncapi.model

@AsyncApiComponent
class TagsList : ArrayList<Tag>() {
    inline fun tag(build: Tag.() -> Unit): Tag =
        Tag().apply(build).also { add(it) }
}

@AsyncApiComponent
class Tag {
    lateinit var name: String
    var description: String? = null
    var externalDocs: ExternalDocumentation? = null

    fun name(value: String): String =
        value.also { name = it }

    fun description(value: String): String =
        value.also { description = it }

    inline fun externalDocs(build: ExternalDocumentation.() -> Unit): ExternalDocumentation =
        ExternalDocumentation().apply(build).also { externalDocs = it }
}
