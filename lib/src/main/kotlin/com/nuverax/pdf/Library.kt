/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package com.nuverax.pdf

import java.io.File


class Library {
    fun someLibraryMethod(): Boolean {

        return true
    }
}

fun main() {
    val setup = NxTemplate()
    setup.load(File("example.json"), File("example.pdf"))
}
