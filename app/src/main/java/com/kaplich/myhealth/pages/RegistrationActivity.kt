package com.kaplich.myhealth.pages

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kaplich.myhealth.NativeLib
import com.kaplich.myhealth.R
import com.kaplich.myhealth.database.DatabaseHelper
import kotlin.random.Random

class RegistrationActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var passwordRepeatEditText: EditText
    private lateinit var signUpButton: Button
    private lateinit var loginButton: Button
    private lateinit var labelTextView: TextView

    private lateinit var dbHelper: DatabaseHelper
    private val nativeLib = NativeLib() // Инициализация NativeLib

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Инициализация генератора случайных чисел
        nativeLib.initRandom()

        dbHelper = DatabaseHelper(this)
        initializeViews()
        setupListeners()
    }

    // Остальной код остается прежним...

    private fun initializeViews() {
        nameEditText = findViewById(R.id.nameEnter)
        passwordEditText = findViewById(R.id.passwordEnter)
        passwordRepeatEditText = findViewById(R.id.passwordRepeat)
        signUpButton = findViewById(R.id.signUpButton)
        loginButton = findViewById(R.id.logInButton)
        labelTextView = findViewById(R.id.label)
    }

    private fun setupListeners() {
        signUpButton.setOnClickListener {
            if (validateRegistrationData()) {
                // Генерация случайного ID пользователя
                val name = nameEditText.text.toString().trim()
                val password = passwordEditText.text.toString().trim()

                // Добавьте данные пользователя в базу
                val newUserId = dbHelper.addUser(name, password)

                if (newUserId != -1L) {
                    Toast.makeText(this, "Регистрация успешна", Toast.LENGTH_SHORT).show()

                    // Передача userId на страницу профиля
                    val intent = Intent(this, ProfileActivity::class.java)
                    intent.putExtra("USER_ID", newUserId.toInt()) // Передача userId
                    startActivity(intent)
                    finish()
                } else {
                    showErrorMessage("Ошибка при регистрации, попробуйте еще раз")
                }
            }
        }

        loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun validateRegistrationData(): Boolean {
        val name = nameEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        val passwordRepeat = passwordRepeatEditText.text.toString().trim()

        return when {
            name.isEmpty() -> {
                showErrorMessage("Пожалуйста, введите имя")
                false
            }
            password.isEmpty() -> {
                showErrorMessage("Пожалуйста, введите пароль")
                false
            }
            password != passwordRepeat -> {
                showErrorMessage("Пароли не совпадают")
                false
            }
            else -> true
        }
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}