package com.nuverax.pdf.core.components

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

enum class NxComponentType(val nameType: String) {
    P("P")
}
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY
)
@JsonSubTypes(value = [
    JsonSubTypes.Type(value = NxParagraph::class, name = "P")
])
open class NxBaseComponent(
    open val id: String = "",
    @JsonProperty("@type")
    open val type: NxComponentType = NxComponentType.P
)

data class NxParagraph(
    override val id: String = "",
    override val type: NxComponentType = NxComponentType.P,
    val value: String,
    val alignment: String
): NxBaseComponent()