package com.example.libraryretrofit.data

data class SeoulPublicLibraryInfo(
    val RESULT: RESULT,
    val list_total_count: Int,
    val row: List<Row>
)