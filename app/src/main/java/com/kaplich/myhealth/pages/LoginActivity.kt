package com.kaplich.myhealth.pages

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kaplich.myhealth.R
import com.kaplich.myhealth.database.DatabaseHelper

class LoginActivity : AppCompatActivity() {

    private lateinit var idEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var signUpButton: Button
    private lateinit var labelTextView: TextView
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        dbHelper = DatabaseHelper(this)
        initializeViews()
        setupListeners()
    }

    private fun initializeViews() {
        idEditText = findViewById(R.id.nameEnter)
        passwordEditText = findViewById(R.id.passwordEnter)
        loginButton = findViewById(R.id.logInButton)
        signUpButton = findViewById(R.id.signUpButton)
        labelTextView = findViewById(R.id.label)
    }

    private fun setupListeners() {
        loginButton.setOnClickListener {
            if (validateCredentials()) {
                val userId = idEditText.text.toString().trim().toIntOrNull()
                val password = passwordEditText.text.toString().trim()

                if (userId != null) {
                    val user = dbHelper.getUserById(userId)
                    if (user != null && user.password == password) {
                        Toast.makeText(this, "Аутентификация успешна", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, ProfileActivity::class.java)
                        intent.putExtra("USER_ID", userId) // Передача userId в ProfileActivity
                        startActivity(intent)
                        finish()
                    } else {
                        showErrorMessage("Неверные учетные данные")
                    }
                } else {
                    showErrorMessage("Пожалуйста, введите корректный ID")
                }
            }
        }

        signUpButton.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun validateCredentials(): Boolean {
        val userId = idEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        return when {
            userId.isEmpty() -> {
                showErrorMessage("Пожалуйста, введите ID")
                false
            }
            password.isEmpty() -> {
                showErrorMessage("Пожалуйста, введите пароль")
                false
            }
            else -> true
        }
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}