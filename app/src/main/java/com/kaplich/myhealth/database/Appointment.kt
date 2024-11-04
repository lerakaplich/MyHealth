package com.kaplich.myhealth.database


data class Appointment(
    val id: Int,
    val userId: Int,
    val doctorSpecialization: String,
    val appointmentDate: String,
    val comment: String?
) {
    // Здесь можно добавить дополнительные методы, если это необходимо
}