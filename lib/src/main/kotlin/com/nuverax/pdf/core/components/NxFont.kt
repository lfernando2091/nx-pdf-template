package com.nuverax.pdf.core.components

import com.itextpdf.text.Font

data class NxFont(
    val size: Float,
    val hexColor: String,
    val fontFamily: String,
    val style: String
) {
     fun convertFontFamily(): Font.FontFamily =
         when(fontFamily) {
             "COURIER" -> Font.FontFamily.COURIER
             "HELVETICA" -> Font.FontFamily.HELVETICA
             "TIMES_ROMAN" -> Font.FontFamily.TIMES_ROMAN
             "SYMBOL" -> Font.FontFamily.SYMBOL
             "ZAPFDINGBATS" -> Font.FontFamily.ZAPFDINGBATS
             "UNDEFINED" -> Font.FontFamily.UNDEFINED
             else -> Font.FontFamily.UNDEFINED
         }
    fun convertStyle(): Int =
        when(style) {
            "NORMAL" -> Font.NORMAL
            "BOLD" -> Font.BOLD
            "ITALIC" -> Font.ITALIC
            "UNDERLINE" -> Font.UNDERLINE
            "STRIKETHRU" -> Font.STRIKETHRU
            "BOLDITALIC" -> Font.BOLDITALIC
            "UNDEFINED" -> Font.UNDEFINED
            else -> Font.UNDEFINED
        }
}