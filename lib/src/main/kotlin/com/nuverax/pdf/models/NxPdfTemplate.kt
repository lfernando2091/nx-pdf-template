package com.nuverax.pdf.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.itextpdf.text.Document
import com.itextpdf.text.pdf.PdfWriter
import com.nuverax.pdf.core.components.NxBaseComponent
import com.nuverax.pdf.data.NxBaseData

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
    val type: VarType = VarType.Str,
    val value: Any?
)
open class BaseLayout(
    open val content: List<NxBaseComponent> = emptyList()
)
data class NxPage(
    override val content: List<NxBaseComponent>
): BaseLayout()
fun BaseLayout.render(
    documentSetup: Pair<Document, PdfWriter>,
    variables: Map<String, NxVariable> = emptyMap(),
    data: NxBaseData<*>? = null
) {
    for (component in this.content) {
        component.render(documentSetup, variables, data)
    }
}
data class NxBody(
    val pages: List<NxPage>
)
data class NxHeader(
    override val content: List<NxBaseComponent>
): BaseLayout()
data class NxFooter(
    override val content: List<NxBaseComponent>
): BaseLayout()
data class NxContent(
    val header: NxHeader?,
    val body: NxBody,
    val footer: NxFooter?
)
data class NxPdfTemplate(
    val document: NxDocument = NxDocument(),
    val variables: Map<String, NxVariable>?,
    val content: NxContent
)