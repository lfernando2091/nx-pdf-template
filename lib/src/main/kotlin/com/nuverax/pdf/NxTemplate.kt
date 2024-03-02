package com.nuverax.pdf

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.itextpdf.text.pdf.PdfPageEventHelper
import com.nuverax.pdf.core.*
import com.nuverax.pdf.models.NxPdfTemplate
import java.io.File


class NxTemplate {
    fun load(input: File, output: File) {
        val mapper = jacksonObjectMapper()
        val data = mapper.readValue<NxPdfTemplate>(input)
        render(
            data,
            output,
            mapOf(
                "var1" to "Hoaoaoaoao"
            )
        )
    }

    fun render(
        nxDoc: NxPdfTemplate,
        output: File,
        inputVars: Map<String, String>? = null
    ) {
        try {
            val varSettings = NxVariablesSetup(nxDoc.variables)
            val finalVars = varSettings.setup(inputVars)
            val events = mutableListOf<PdfPageEventHelper>()
            //region Header Config
            if (nxDoc.content.header != null) {
                val header = Header(
                    nxDoc.content.header,
                    finalVars
                )
                events.add(header)
            }
            //endregion
            //region Footer Config
            if (nxDoc.content.footer != null) {
                val header = Footer(
                    nxDoc.content.footer,
                    finalVars
                )
                events.add(header)
            }
            //endregion
            //region Body Config
            val nxDocument = NxDocumentSetup(
                nxDoc.document,
                finalVars,
                events
            )
            val documentSetup = nxDocument.setup(output)
            val content = NxContentSetup(documentSetup, finalVars)
            content.setup(nxDoc.content.body)
            //endregion
            documentSetup.first.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}