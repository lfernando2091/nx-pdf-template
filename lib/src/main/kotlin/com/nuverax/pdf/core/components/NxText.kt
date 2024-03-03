package com.nuverax.pdf.core.components

import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import com.nuverax.pdf.data.JsonData
import com.nuverax.pdf.data.NxBaseData
import com.nuverax.pdf.models.NxVariable
import com.nuverax.pdf.utils.processData
import com.nuverax.pdf.utils.processVars

data class NxParagraph(
    override val id: String = "",
    val value: String,
    val alignment: NxAlignment
): NxBaseComponent(type = NxComponentType.P) {
    override fun render(
        documentSetup: Pair<Document, PdfWriter>,
        variables: Map<String, NxVariable>,
        data: NxBaseData<*>?
    ) {
        val paragraph = Paragraph(
            value.processVars(variables)
                .processData(data)
        )
        paragraph.alignment = alignment.converter()
        documentSetup.first.add(paragraph)
    }
}