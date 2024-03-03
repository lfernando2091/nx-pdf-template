package com.nuverax.pdf.core.components

import com.fasterxml.jackson.annotation.JsonProperty
import com.itextpdf.text.Document
import com.itextpdf.text.Rectangle
import com.itextpdf.text.pdf.PdfWriter
import com.nuverax.pdf.data.NxBaseData
import com.nuverax.pdf.models.NxVariable

data class NxRectangle(
    override val id: String = "",
    val size: NxSize,
    @JsonProperty("border_width")
    val borderWidth: Float = 2f
): NxBaseComponent(type = NxComponentType.RECT) {
    override fun render(
        documentSetup: Pair<Document, PdfWriter>,
        variables: Map<String, NxVariable>,
        data: NxBaseData<*>?
    ) {
        val pdfContentByte = documentSetup.second.directContent
        val rect = Rectangle(size.left, size.bottom,size.right,size.top)
        rect.border = Rectangle.BOX
        rect.borderWidth = borderWidth
        pdfContentByte.rectangle(rect)
    }
}