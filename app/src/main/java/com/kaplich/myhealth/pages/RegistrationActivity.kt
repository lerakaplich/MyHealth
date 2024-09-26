package com.kaplich.myhealth.pages

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kaplich.myhealth.R

class RegistrationActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var passwordRepeatEditText: EditText
    private lateinit var signUpButton: Button
    private lateinit var loginButton: Button
    private lateinit var labelTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        initializeViews()

        setupListeners()
    }

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
                // Если учетные данные валидны, переходим на страницу профиля
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        loginButton.setOnClickListener{
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
            else -> {
                // Здесь должна быть логика регистрации
                // Например, запрос к серверу или сохранение в локальном хранилище
                // Для простоты примера мы просто завершаем активность
                finish()
                true
            }
        }
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
