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
                val current = mutate.get(key)!!
                val type = VarType.valueOf(current.type)
                when(type) {
                    VarType.Str -> mutate.put(key, NxVariable(type.nameType, value))
                    VarType.Int -> mutate.put(key, NxVariable(type.nameType, value.toIntOrNull()))
                    VarType.Flt -> mutate.put(key, NxVariable(type.nameType, value.toFloatOrNull()))
                }
            }
        }
        return mutate
    }
}