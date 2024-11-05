package com.kaplich.myhealth.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlin.random.Random

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "myhealth.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE users (_id INTEGER PRIMARY KEY, name TEXT, password TEXT)")
        db.execSQL("CREATE TABLE appointments (_id INTEGER PRIMARY KEY, userId INTEGER, doctorSpecialization TEXT, appointmentDate TEXT, comment TEXT)")
        db.execSQL("CREATE TABLE analyses (_id INTEGER PRIMARY KEY, userId INTEGER, analysisDate TEXT, comment TEXT)")
        db.execSQL("CREATE TABLE medications (_id INTEGER PRIMARY KEY, userId INTEGER, name TEXT, frequency TEXT, type TEXT, startDate TEXT, endDate TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS users")
        db.execSQL("DROP TABLE IF EXISTS appointments")
        db.execSQL("DROP TABLE IF EXISTS analyses")
        db.execSQL("DROP TABLE IF EXISTS medications")
        onCreate(db)
    }

    private fun generateUniqueId(): Int {
        var randomId: Int
        do {
            randomId = Random.nextInt(10000000, 100000000)
        } while (isIdExists(randomId))
        return randomId
    }

    private fun isIdExists(id: Int): Boolean {
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT COUNT(*) FROM users WHERE _id = ?", arrayOf(id.toString()))
        cursor.moveToFirst()
        val exists = cursor.getInt(0) > 0
        cursor.close()
        return exists
    }

    fun addUser(name: String, password: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("_id", generateUniqueId())
            put("name", name)
            put("password", password)
        }
        return db.insert("users", null, values)
    }

    fun getUser(userId: Int): User? {
        val db = readableDatabase
        val cursor = db.query(
            "users",
            arrayOf("_id", "name", "password"), // Добавляем password для соответствия классу User
            "_id = ?",
            arrayOf(userId.toString()),
            null,
            null,
            null
        )

        return if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"))
            val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
            val password = cursor.getString(cursor.getColumnIndexOrThrow("password"))
            User(id, name, password) // Возвращаем объект User с заполненными данными
        } else {
            null
        }.also {
            cursor.close()
        }
    }




    // Получение пользователя по ID
    fun getUserById(userId: Int): User? {
        val db = readableDatabase
        val cursor: Cursor = db.query(
            "users",
            arrayOf("_id", "name", "password"), // Добавляем password
            "_id = ?",
            arrayOf(userId.toString()),
            null,
            null,
            null
        )

        return if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"))
            val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
            val password = cursor.getString(cursor.getColumnIndexOrThrow("password")) // Получаем password
            User(id, name, password) // Передаем три параметра в конструктор
        } else {
            null
        }.also {
            cursor.close()
        }
    }



    // Методы для работы с записями
    fun addAppointment(userId: Int, doctorSpecialization: String, appointmentDate: String, comment: String?): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("_id", generateUniqueId())
            put("userId", userId)
            put("doctorSpecialization", doctorSpecialization)
            put("appointmentDate", appointmentDate)
            put("comment", comment)
        }
        return db.insert("appointments", null, values)
    }

    fun getAppointments(userId: Int): Cursor {
        val db = readableDatabase
        return db.query(
            "appointments",
            arrayOf("_id", "userId", "doctorSpecialization", "appointmentDate", "comment"),
            "userId = ?",
            arrayOf(userId.toString()),
            null,
            null,
            null
        )
    }

    fun addAnalysis(userId: Int, analysisDate: String, comment: String?): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("_id", generateUniqueId())
            put("userId", userId)
            put("analysisDate", analysisDate)
            put("comment", comment)
        }
        return db.insert("analyses", null, values)
    }

    fun getAnalyses(userId: Int): Cursor {
        val db = readableDatabase
        return db.query(
            "analyses",
            arrayOf("_id", "userId", "analysisDate", "comment"),
            "userId = ?",
            arrayOf(userId.toString()),
            null,
            null,
            null
        )
    }

    fun addMedication(medication: Medication): Long {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put("_id", generateUniqueId())
            put("userId", medication.userId)
            put("name", medication.name)
            put("frequency", medication.frequency)
            put("type", medication.type)
            put("startDate", medication.startDate)
            put("endDate", medication.endDate)
        }
        return db.insert("medications", null, contentValues)
    }

    fun getMedications(userId: Int): Cursor {
        val db = readableDatabase
        return db.query(
            "medications",
            arrayOf("_id", "userId", "name", "frequency", "type", "startDate", "endDate"),
            "userId = ?",
            arrayOf(userId.toString()),
            null,
            null,
            null // Добавляем `null` для аргумента `orderBy`
        )
    }



    fun updateUserInfo(userId: Int, newName: String?, newPassword: String?): Boolean {
        val db = writableDatabase
        val values = ContentValues()

        if (newName != null) values.put("name", newName)
        if (newPassword != null) values.put("password", newPassword)

        return if (values.size() > 0) {
            val rowsAffected = db.update("users", values, "_id = ?", arrayOf(userId.toString()))
            rowsAffected > 0
        } else {
            false
        }
    }


    fun deleteUser(userId: Int): Boolean {
        val db = writableDatabase
        val rowsAffected = db.delete("users", "_id = ?", arrayOf(userId.toString()))
        return rowsAffected > 0
    }
    fun getUserWithDetails(userId: Int): User? {
        val user = getUser(userId)
        val appointments = getAppointments(userId).use { cursor ->
            generateSequence { if (cursor.moveToNext()) cursor else null }
                .map { Appointment(cursor.getInt(0), userId, cursor.getString(2), cursor.getString(3), cursor.getString(4)) }
                .toList()
        }
        val medications = getMedications(userId).use { cursor ->
            generateSequence { if (cursor.moveToNext()) cursor else null }
                .map { Medication(cursor.getInt(0), userId, cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6)) }
                .toList()
        }
        val analyses = getAnalyses(userId).use { cursor ->
            generateSequence { if (cursor.moveToNext()) cursor else null }
                .map { Analysis(cursor.getInt(0), userId, cursor.getString(2), cursor.getString(3)) }
                .toList()
        }
        return user?.copy(appointments = appointments, medications = medications, analyses = analyses)
    }

}
