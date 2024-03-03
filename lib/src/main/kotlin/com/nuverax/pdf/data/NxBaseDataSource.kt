package com.nuverax.pdf.data
enum class NxDataSourceType(val nameType: String) {
    json("json"),
    db("db")
}
open class NxBaseDataSource<T>(
    open val type: NxDataSourceType = NxDataSourceType.json
) {
    open fun iterate(): List<T>? = TODO("Implement this method")
    open fun size(): Int = TODO("Implement this method")
}

open class NxBaseData<T>(
    open val data: T,
    open val type: NxDataSourceType = NxDataSourceType.json,
) {
    open fun retrieve(input: String): String? = TODO("Implement this method")
}
