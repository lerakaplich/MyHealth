package com.kaplich.myhealth.database

data class User(
    val id: Int,
    val name: String,
    val password: String,
    val appointments: List<Appointment> = listOf(),
    val medications: List<Medication> = listOf(),
    val analyses: List<Analysis> = listOf()
)
