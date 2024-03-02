package com.nuverax.pdf.core

import com.itextpdf.text.Document
import com.itextpdf.text.PageSize
import com.itextpdf.text.pdf.PdfPageEvent
import com.itextpdf.text.pdf.PdfWriter
import com.nuverax.pdf.models.NxDocument
import com.nuverax.pdf.models.NxVariable
import com.nuverax.pdf.utils.processVars
import java.io.File
import java.io.FileOutputStream

class NxDocumentSetup(
    private val nxDocument: NxDocument,
    private val variables: Map<String, NxVariable> = emptyMap(),
    private val events: List<PdfPageEvent> = emptyList()
) {
    fun setup(output: File): Document {
        val document = Document(
            PageSize.getRectangle(nxDocument.pageSize),
            nxDocument.margins.left,
            nxDocument.margins.right,
            nxDocument.margins.top,
            nxDocument.margins.bottom
        )
        val writer = PdfWriter.getInstance(document, FileOutputStream(output))
        for(event in events) {
            writer.pageEvent = event
        }
        document.open()
        if (nxDocument.title != null)
            document.addTitle(nxDocument.title.processVars(variables))
        if (nxDocument.author != null)
            document.addAuthor(nxDocument.author.processVars(variables))
        if (nxDocument.creator != null)
            document.addCreator(nxDocument.creator.processVars(variables))
        if (nxDocument.keyWords != null)
            document.addKeywords(nxDocument.keyWords.processVars(variables))
        if (nxDocument.language != null)
            document.addLanguage(nxDocument.language.processVars(variables))
        if (nxDocument.subject != null)
            document.addSubject(nxDocument.subject.processVars(variables))
        return document
    }
}