package com.kaplich.myhealth

object NativeLib {
    init {
        System.loadLibrary("native_lib")
    }

    external fun registerUser(name: String, password: String): Long
    external fun loginUser(userId: Int, password: String): Boolean
    external fun getUserName(userId: Long): String // Добавьте этот метод
}
