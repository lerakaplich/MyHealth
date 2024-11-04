package com.kaplich.myhealth.database

data class Analysis(
    val id: Int,
    val userId: Int,
    val analysisDate: String,
    val comment: String?
)