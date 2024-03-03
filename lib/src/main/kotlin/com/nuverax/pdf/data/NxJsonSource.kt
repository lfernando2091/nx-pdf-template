package com.nuverax.pdf.data

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.nuverax.pdf.utils.ARRAY_REGEX
import com.nuverax.pdf.utils.arrayIndexes
import com.nuverax.pdf.utils.hasArraysDefinition
import java.io.File

class JsonData(
    data: JsonNode
): NxBaseData<JsonNode>(
    data,
    NxDataSourceType.json
) {
    override fun retrieve(input: String): String? {
        var current = data
        val properties = input.split(".")
        for (part in properties) {
            if (part.hasArraysDefinition()) {
                val propertyName = part.replace(ARRAY_REGEX.toRegex(), "")
                current = current.get(propertyName)
                val indexes = part.arrayIndexes()
                for (index in indexes) {
                    current = current.get(index)
                }
            } else {
                current = current.get(part)
            }
        }
        return current.asText()
    }
}

class NxJsonSource: NxBaseDataSource<JsonData>(
    NxDataSourceType.json
) {
    private val mapper = jacksonObjectMapper()
    private var root: JsonNode? = null
    fun read(location: File) {
        root = mapper.readTree(location)
    }
    fun read(data: String) {
        root = mapper.readTree(data)
    }
    override fun iterate(): List<JsonData>? {
        if (root == null) return null
        return root!!.map { e -> JsonData(e) }
    }
    override fun size(): Int {
        if (root == null) return 0
        if (!root!!.isArray) return 0
        return root!!.size()
    }
}