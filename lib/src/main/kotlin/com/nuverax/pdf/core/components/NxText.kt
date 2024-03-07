package com.nuverax.pdf.core.components

import com.itextpdf.text.*
import com.itextpdf.text.pdf.ColumnText
import com.itextpdf.text.pdf.PdfWriter
import com.nuverax.pdf.data.JsonData
import com.nuverax.pdf.data.NxBaseData
import com.nuverax.pdf.models.NxVariable
import com.nuverax.pdf.utils.hexToRgba
import com.nuverax.pdf.utils.processData
import com.nuverax.pdf.utils.processVars

data class NxParagraph(
    override val id: String = "",
    val value: String,
    val alignment: NxAlignment = NxAlignment.ALIGN_LEFT,
    val size: NxSize = NxSize(100f, 50f),
    val position: NxPosition = NxPosition(0f, 0f),
    val font: NxFont = NxFont(12f, "#000000", "HELVETICA", "NORMAL")
): NxBaseComponent(type = NxComponentType.P) {
    override fun render(
        documentSetup: Pair<Document, PdfWriter>,
        variables: Map<String, NxVariable>,
        data: NxBaseData<*>?
    ) {
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
            font.convertFontFamily(),
            font.size,
            font.convertStyle(),
            font.hexColor.hexToRgba()
        )
        val pa = Paragraph(
            value.processVars(variables)
                .processData(data),
            font
        )
        txt.setText(pa)
        txt.setLeading(0f, 1.1f)
        txt.go()
    }
}