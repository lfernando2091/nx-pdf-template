package com.nuverax.pdf.utils

import com.nuverax.pdf.data.JsonData
import com.nuverax.pdf.data.NxBaseData
import com.nuverax.pdf.models.NxVariable
import java.util.regex.Pattern

val VAR_REGEX: Pattern = Pattern.compile("\\$\\{(.*?)}")
val LOCAL_VAR_REGEX: Pattern = Pattern.compile("var\\((.*?)\\)")
val JSON_VAR_REGEX: Pattern = Pattern.compile("json\\((.*?)\\)")
val SINGLE_JSON_VAR_REGEX: Pattern = Pattern.compile("\\$\\{json\\((.*?)\\)}")
val SQL_VAR_REGEX: Pattern = Pattern.compile("sql\\((.*?)\\)")

public fun String.hasVariables() = VAR_REGEX.matcher(this).find()

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
public fun String.processData(data: NxBaseData<*>?): String {
    if (data != null && this.hasVariables()) {
        var updated: String = this
        when(data) {
            is JsonData -> {
                val varMatcher = VAR_REGEX.matcher(updated)
                val hmap = mutableMapOf<String, String?>()
                while(varMatcher.find()) {
                    val keyMatch = varMatcher.group(0)
                    if (hmap.containsKey(keyMatch)) continue
                    val jsonMatcher = JSON_VAR_REGEX.matcher(varMatcher.group(1))
                    if (jsonMatcher.find()) {
                        val valueMatch = data.retrieve(jsonMatcher.group(1))
                        hmap[keyMatch] = valueMatch
                    }
                    updated = updated.replace(keyMatch, hmap[keyMatch] ?: "null")
                }
            }
        }
        return updated
    }
    return this
}