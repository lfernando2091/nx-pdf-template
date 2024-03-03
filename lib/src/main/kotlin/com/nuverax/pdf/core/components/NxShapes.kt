package com.nuverax.pdf.core.components

import com.fasterxml.jackson.annotation.JsonProperty
import com.itextpdf.text.Document
import com.itextpdf.text.Rectangle
import com.itextpdf.text.pdf.PdfWriter
import com.nuverax.pdf.data.NxBaseData
import com.nuverax.pdf.models.NxVariable
/*
{
    "@type": "RECT",
    "size": {
      "width": 100,
      "height": 200
    },
    "position": {
      "x": 0,
      "y": 0
    },
    "border_width": 2
}
 */
data class NxRectangle(
    override val id: String = "",
    val size: NxSize = NxSize(100f, 50f),
    val position: NxPosition = NxPosition(0f, 0f),
    @JsonProperty("border_width")
    val borderWidth: Float = 2f
): NxBaseComponent(type = NxComponentType.RECT) {
    override fun render(
        documentSetup: Pair<Document, PdfWriter>,
        variables: Map<String, NxVariable>,
        data: NxBaseData<*>?
    ) {
        val pageSize = documentSetup.first.pageSize
        val finalPosition = size.rectangle(position, pageSize)
        val pdfContentByte = documentSetup.second.directContent
        val rect = Rectangle(
            finalPosition.left,
            finalPosition.bottom,
            finalPosition.right,
            finalPosition.top
        )
        rect.border = Rectangle.BOX
        rect.borderWidth = borderWidth
        pdfContentByte.rectangle(rect)
    }
}