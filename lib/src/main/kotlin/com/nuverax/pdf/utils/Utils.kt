package com.nuverax.pdf.utils

import com.nuverax.pdf.models.NxVariable
import java.util.regex.Pattern

class Utils {
}

val variableRex = Pattern.compile("\\$\\{(.*?)}");

public fun String.hasVariables() = variableRex.matcher(this).find()

public fun String.processVars(vars: Map<String, NxVariable>): String {
    if (this.hasVariables()) {
        var updated: String = this
        for ((k,v) in vars.entries) {
            updated = updated.replace("\${$k}", v.value.toString())
        }
        return updated
    }
    return this
}