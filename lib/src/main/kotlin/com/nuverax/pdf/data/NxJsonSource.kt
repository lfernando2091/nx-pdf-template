package com.nuverax.pdf.data

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.File

class JsonData(
    data: JsonNode
): NxBaseData<JsonNode>(
    data,
    NxDataSourceType.json
) {
    override fun retrieve(input: String): String? {
        return super.retrieve(input)
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