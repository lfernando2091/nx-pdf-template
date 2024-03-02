package com.nuverax.pdf.core.components

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.itextpdf.text.Document
import com.itextpdf.text.pdf.PdfWriter
import com.nuverax.pdf.models.NxVariable

enum class NxComponentType(val nameType: String) {
    P("P"),
    RECT("RECT")
}
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY
)
@JsonSubTypes(value = [
    JsonSubTypes.Type(value = NxParagraph::class, name = "P"),
    JsonSubTypes.Type(value = NxRectangle::class, name = "RECT")
])
open class NxBaseComponent(
    open val id: String = "",
    @JsonProperty("@type")
    open val type: NxComponentType = NxComponentType.P
) {
    open fun render(
        documentSetup: Pair<Document, PdfWriter>,
        variables: Map<String, NxVariable> = emptyMap()
    ) {
        TODO("Implement render method")
    }
}