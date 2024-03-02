package com.nuverax.pdf.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.nuverax.pdf.core.components.NxBaseComponent

data class Margins(
    val left: Float = 0f,
    val right: Float= 0f,
    val bottom: Float= 0f,
    val top: Float= 0f
)
data class NxDocument(
    val title: String? = null,
    val author: String? = null,
    val creator: String? = null,
    @JsonProperty("key_words")
    val keyWords: String? = null,
    val language: String? = null,
    val subject: String? = null,
    @JsonProperty("page_size")
    val pageSize: String = "A4",
    val margins: Margins = Margins(
        36.0f, 36.0f, 36.0f, 36.0f
    )
)
enum class VarType(val nameType: String) {
    Int("Int"),
    Str("Str"),
    Flt("Flt"),
}
data class NxVariable(
    val type: String = VarType.Str.nameType,
    val value: Any?
)
data class NxPage(
    val content: List<NxBaseComponent>
)
data class NxBody(
    val pages: List<NxPage>
)
data class NxContent(
    val header: Any?,
    val body: NxBody,
    val footer: Any?
)
data class NxPdfTemplate(
    val document: NxDocument = NxDocument(),
    val variables: Map<String, NxVariable>?,
    val content: NxContent
)