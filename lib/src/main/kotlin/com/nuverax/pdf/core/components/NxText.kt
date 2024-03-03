package com.nuverax.pdf.core.components

import com.itextpdf.text.*
import com.itextpdf.text.pdf.ColumnText
import com.itextpdf.text.pdf.PdfWriter
import com.nuverax.pdf.data.JsonData
import com.nuverax.pdf.data.NxBaseData
import com.nuverax.pdf.models.NxVariable
import com.nuverax.pdf.utils.processData
import com.nuverax.pdf.utils.processVars



data class NxParagraph(
    override val id: String = "",
    val value: String,
    val alignment: NxAlignment = NxAlignment.ALIGN_LEFT,
    val size: NxSize = NxSize(100f, 50f),
    val position: NxPosition = NxPosition(0f, 0f),
): NxBaseComponent(type = NxComponentType.P) {
    override fun render(
        documentSetup: Pair<Document, PdfWriter>,
        variables: Map<String, NxVariable>,
        data: NxBaseData<*>?
    ) {
//        val paragraph = Paragraph(
//            value.processVars(variables)
//                .processData(data)
//        )
//        paragraph.alignment = alignment.converter()
//        documentSetup.first.add(paragraph)
        val pageSize = documentSetup.first.pageSize
        val finalPosition = size.rectangle(position, pageSize)
        val txt = ColumnText(documentSetup.second.directContent)
        // left,bottom,right,top
        txt.setSimpleColumn(
            finalPosition.left,
            finalPosition.bottom,
            finalPosition.right,
            finalPosition.top
        )
        txt.alignment = alignment.converter()
        val font = Font(
            Font.FontFamily.HELVETICA,
            12f,
            Font.ITALIC,
            BaseColor.GRAY
        )
        txt.setText(Phrase(
            value.processVars(variables)
                .processData(data),
            font
        ))
        txt.go()
    }
}