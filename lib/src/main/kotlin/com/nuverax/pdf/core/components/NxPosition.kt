package com.nuverax.pdf.core.components

import com.itextpdf.text.Element

enum class NxAlignment(val nameType: String) {
    ALIGN_RIGHT("ALIGN_RIGHT")
}
fun NxAlignment.converter(): Int = when(this) {
    NxAlignment.ALIGN_RIGHT -> Element.ALIGN_RIGHT
}