package com.nuverax.pdf.core.components

import com.itextpdf.text.Element

enum class NxAlignment(val nameType: String) {
    ALIGN_RIGHT("ALIGN_RIGHT"),
    ALIGN_CENTER("ALIGN_CENTER"),
    ALIGN_LEFT("ALIGN_LEFT"),
    ALIGN_JUSTIFIED("ALIGN_JUSTIFIED")
}
fun NxAlignment.converter(): Int = when(this) {
    NxAlignment.ALIGN_RIGHT -> Element.ALIGN_RIGHT
    NxAlignment.ALIGN_CENTER -> Element.ALIGN_CENTER
    NxAlignment.ALIGN_LEFT -> Element.ALIGN_LEFT
    NxAlignment.ALIGN_JUSTIFIED -> Element.ALIGN_JUSTIFIED
}