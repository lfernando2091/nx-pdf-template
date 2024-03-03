package com.nuverax.pdf.utils

import com.itextpdf.text.BaseColor
import com.nuverax.pdf.data.JsonData
import com.nuverax.pdf.data.NxBaseData
import com.nuverax.pdf.models.NxVariable
import java.util.regex.Pattern

val ARRAY_REGEX: Pattern = Pattern.compile("\\[(.*?)]")
val VAR_REGEX: Pattern = Pattern.compile("\\$\\{(.*?)}")
val LOCAL_VAR_REGEX: Pattern = Pattern.compile("var\\((.*?)\\)")
val JSON_VAR_REGEX: Pattern = Pattern.compile("json\\((.*?)\\)")
val SINGLE_JSON_VAR_REGEX: Pattern = Pattern.compile("\\$\\{json\\((.*?)\\)}")
val SQL_VAR_REGEX: Pattern = Pattern.compile("sql\\((.*?)\\)")

public fun String.hasVariables() = VAR_REGEX.matcher(this).find()
public fun String.hasArraysDefinition() = ARRAY_REGEX.matcher(this).find()
public fun String.arrayIndexes(): List<Int> {
    val result = mutableListOf<Int>()
    val matcher = ARRAY_REGEX.matcher(this)
    while(matcher.find()) {
        result.add(matcher.group(1).toInt())
    }
    return result
}

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
                val varMatcher = SINGLE_JSON_VAR_REGEX.matcher(updated)
                val hmap = mutableMapOf<String, String?>()
                while(varMatcher.find()) {
                    val keyMatch = varMatcher.group(0)
                    if (hmap.containsKey(keyMatch)) continue
                    hmap[keyMatch] = data.retrieve(varMatcher.group(1))
                    updated = updated.replace(keyMatch, hmap[keyMatch] ?: "null")
                }
            }
        }
        return updated
    }
    return this
}

public fun String.hexToRgba(): BaseColor = when(this.length){
        7 -> BaseColor(
            Integer.valueOf( this.substring( 1, 3 ), 16 ),
            Integer.valueOf( this.substring( 3, 5 ), 16 ),
            Integer.valueOf( this.substring( 5, 7 ), 16 )
        )
        9 -> BaseColor(
            Integer.valueOf( this.substring( 1, 3 ), 16 ),
            Integer.valueOf( this.substring( 3, 5 ), 16 ),
            Integer.valueOf( this.substring( 5, 7 ), 16 ),
            Integer.valueOf( this.substring( 7, 9 ), 16 )
        )

    else -> BaseColor(0f, 0f, 0f)
}