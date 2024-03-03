package com.nuverax.pdf.core

import com.nuverax.pdf.models.NxVariable
import com.nuverax.pdf.models.VarType

class NxVariablesSetup(
    private val default: Map<String, NxVariable>?
) {
    fun setup(
        input: Map<String, String>?
    ): Map<String, NxVariable> {
        if(input == null) return default ?: emptyMap()
        if (default == null) return emptyMap()
        val mutate = mutableMapOf<String, NxVariable>()
        mutate.putAll(default)
        for ((key, value) in input.entries) {
            if (mutate.containsKey(key)) {
                val current = mutate[key]!!
                when(current.type) {
                    VarType.Str -> mutate[key] = NxVariable(current.type, value)
                    VarType.Int -> mutate[key] = NxVariable(current.type, value.toIntOrNull())
                    VarType.Flt -> mutate[key] = NxVariable(current.type, value.toFloatOrNull())
                }
            }
        }
        return mutate
    }
}