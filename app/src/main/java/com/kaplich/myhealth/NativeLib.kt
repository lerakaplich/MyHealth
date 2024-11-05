package com.kaplich.myhealth

class NativeLib {
    init {
        System.loadLibrary("native_lib") // Название вашей нативной библиотеки
    }

    external fun initRandom() // Новый метод для инициализации генератора случайных чисел
    external fun registerUser(name: String, password: String): Long
    external fun getUserName(userId: Long): String?
}
