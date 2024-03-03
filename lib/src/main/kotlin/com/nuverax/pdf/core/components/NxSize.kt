package com.nuverax.pdf.core.components

import com.itextpdf.text.Rectangle

data class NxRectangleSize(
    val right: Float,
    val left: Float,
    val top: Float,
    val bottom: Float
)

data class NxPosition(
    val x: Float,
    val y: Float
)

data class NxSize(
    val width: Float,
    val height: Float
) {
    /*
    "size": {
                "right": 100,
                "left": 0,
                "top": 100,
                "bottom": 0
              },
     */
    fun rectangle(
        position: NxPosition,
        pageSize: Rectangle
    ): NxRectangleSize {
        return NxRectangleSize(
            position.x + width, position.x, pageSize.top - position.y, pageSize.top - height - position.y
        )
    }
}