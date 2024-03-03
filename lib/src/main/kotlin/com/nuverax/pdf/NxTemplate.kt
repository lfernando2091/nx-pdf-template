package com.nuverax.pdf

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.itextpdf.text.pdf.PdfPageEventHelper
import com.nuverax.pdf.core.*
import com.nuverax.pdf.data.NxBaseDataSource
import com.nuverax.pdf.data.NxJsonSource
import com.nuverax.pdf.models.NxPdfTemplate
import java.io.File


class NxTemplate<T>(
    private val source: NxBaseDataSource<T>? = null
) {
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
            val header = if (nxDoc.content.header != null) {
                val h = Header(
                    nxDoc.content.header,
                    finalVars
                )
                events.add(h)
                h
            } else null
            //endregion
            //region Footer Config
            val footer = if (nxDoc.content.footer != null) {
                val f = Footer(
                    nxDoc.content.footer,
                    finalVars
                )
                events.add(f)
                f
            } else null
            //endregion
            if (source != null) {
                when(source) {
                    is NxJsonSource -> {
                        for (el in source.iterate()!!) {
                            if (header != null)
                                header.data = el
                            if (footer != null)
                                footer.data = el

                            println(el)
                        }
                    }
                }
            } else {
                //region Body Config
                val nxDocument = NxDocumentSetup(
                    nxDoc.document,
                    finalVars,
                    events
                )
                val documentSetup = nxDocument.setup(output)
                val content = NxContentSetup(documentSetup, finalVars)
                content.setup(nxDoc.content.body)
                documentSetup.first.close()
                //endregion
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}