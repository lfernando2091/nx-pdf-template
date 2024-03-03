package com.nuverax.pdf.core

import com.itextpdf.text.Document
import com.itextpdf.text.pdf.PdfPageEventHelper
import com.itextpdf.text.pdf.PdfWriter
import com.nuverax.pdf.data.JsonData
import com.nuverax.pdf.data.NxBaseData
import com.nuverax.pdf.models.*

class Header(
    private val content: NxHeader,
    private val variables: Map<String, NxVariable> = emptyMap(),
    var data: NxBaseData<*>? = null
): PdfPageEventHelper() {

    override fun onEndPage(writer: PdfWriter, document: Document) {
        for (component in content.content) {
            component.render(Pair(document, writer), variables, data)
        }
    }
}
class Footer(
    private val content: NxFooter,
    private val variables: Map<String, NxVariable> = emptyMap(),
    var data: NxBaseData<*>? = null
): PdfPageEventHelper() {
    override fun onEndPage(writer: PdfWriter, document: Document) {
        for (component in content.content) {
            component.render(Pair(document, writer), variables, data)
        }
    }
}
class NxContentSetup(
    private val documentSetup: Pair<Document, PdfWriter>,
    private val variables: Map<String, NxVariable> = emptyMap()
) {
    fun setup(content: NxBody) {
        if (content.pages.isEmpty()) return
        content.pages[0].render(documentSetup, variables)
        if (content.pages.size > 1) {
            for(i in 1 until content.pages.size) {
                documentSetup.first.newPage()
                content.pages[i].render(documentSetup, variables)
            }
        }
    }
}