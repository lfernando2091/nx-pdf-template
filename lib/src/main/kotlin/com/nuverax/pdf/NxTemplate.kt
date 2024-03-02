package com.nuverax.pdf

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.nuverax.pdf.core.Header
import com.nuverax.pdf.core.NxContentSetup
import com.nuverax.pdf.core.NxDocumentSetup
import com.nuverax.pdf.core.NxVariablesSetup
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
            val header = Header(
                nxDoc.content.header!!,
                finalVars
            )
            val nxDocument = NxDocumentSetup(
                nxDoc.document,
                finalVars,
                listOf(header)
            )
            val document = nxDocument.setup(output)
            val content = NxContentSetup(document, finalVars)
            content.setup(nxDoc.content.body)
            document.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}