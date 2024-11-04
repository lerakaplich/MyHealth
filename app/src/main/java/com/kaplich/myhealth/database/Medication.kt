package com.kaplich.myhealth.database

data class Medication(
    val id: Int,
    val userId: Int,
    val name: String,
    val frequency: String,
    val type: String,
    val startDate: String,
    val endDate: String
)