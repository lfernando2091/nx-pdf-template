package com.nuverax.pdf.core

import com.itextpdf.text.Document
import com.itextpdf.text.Element
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfPageEventHelper
import com.itextpdf.text.pdf.PdfWriter
import com.nuverax.pdf.core.components.NxComponentType
import com.nuverax.pdf.core.components.NxParagraph
import com.nuverax.pdf.models.NxBody
import com.nuverax.pdf.models.NxPage
import com.nuverax.pdf.models.NxVariable
import com.nuverax.pdf.utils.processVars

class Header(
    private val content: Any,
    private val variables: Map<String, NxVariable> = emptyMap()
): PdfPageEventHelper() {
    override fun onEndPage(writer: PdfWriter, document: Document) {
        val paragraph = Paragraph("This is right aligned text")
        paragraph.alignment = Element.ALIGN_RIGHT
        document.add(paragraph)
    }
}

class NxContentSetup(
    private val document: Document,
    private val variables: Map<String, NxVariable> = emptyMap()
) {
    fun setup(content: NxBody) {
        if (content.pages.isEmpty()) return
        renderPage(content.pages[0])
        if (content.pages.size > 1) {
            for(i in 1 until content.pages.size) {
                document.newPage()
                renderPage(content.pages[i])
            }
        }
    }
    fun renderPage(page: NxPage) {
        for (component in page.content) {
            when(component.type) {
                NxComponentType.P -> renderParagraph(component as NxParagraph)
            }
        }
    }

    fun renderParagraph(data: NxParagraph) {
        val paragraph = Paragraph(data.value.processVars(variables))
        paragraph.alignment = alignConverter(data.alignment)
        document.add(paragraph)
    }

    fun alignConverter(type: String): Int = when(type) {
        "ALIGN_RIGHT" -> Element.ALIGN_RIGHT
        else -> Element.ALIGN_LEFT
    }
}