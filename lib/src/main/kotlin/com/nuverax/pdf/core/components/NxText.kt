package com.nuverax.pdf.core.components

import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import com.nuverax.pdf.models.NxVariable
import com.nuverax.pdf.utils.processVars

data class NxParagraph(
    override val id: String = "",
    override val type: NxComponentType = NxComponentType.P,
    val value: String,
    val alignment: NxAlignment
): NxBaseComponent() {
    override fun render(
        documentSetup: Pair<Document, PdfWriter>,
        variables: Map<String, NxVariable>
    ) {
        val paragraph = Paragraph(value.processVars(variables))
        paragraph.alignment = alignment.converter()
        documentSetup.first.add(paragraph)
    }
}