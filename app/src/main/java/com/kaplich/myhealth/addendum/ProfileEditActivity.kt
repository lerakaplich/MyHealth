package com.kaplich.myhealth.addendum


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kaplich.myhealth.R
import com.kaplich.myhealth.pages.MainPageActivity
import com.kaplich.myhealth.pages.ProfileActivity

class ProfileEditActivity : AppCompatActivity() {

    private lateinit var mainButton: Button
    private lateinit var profileButton: Button
    private lateinit var saveButton: Button
    private lateinit var backButton: Button
    private lateinit var deleteButton: Button
    private lateinit var loginTextView: TextView
    private lateinit var newNameEditText: EditText
    private lateinit var oldPasswordEditText: EditText
    private lateinit var newPasswordEditText: EditText
    private lateinit var newPasswordRepeatEditText: EditText
    private lateinit var titleTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        initializeViews()
        setupListeners()
    }

    private fun initializeViews() {
        mainButton = findViewById(R.id.mainButton)
        profileButton = findViewById(R.id.profileButton)
        saveButton = findViewById(R.id.saveButton)
        backButton = findViewById(R.id.backButton)
        deleteButton = findViewById(R.id.deleteButton)
        loginTextView = findViewById(R.id.login)
        newNameEditText = findViewById(R.id.newNameEnter)
        oldPasswordEditText = findViewById(R.id.oldPasswordEnter)
        newPasswordEditText = findViewById(R.id.newPasswordEnter)
        newPasswordRepeatEditText = findViewById(R.id.newPasswordRepeat)
        titleTextView = findViewById(R.id.edItPassword)
    }

    private fun setupListeners() {
        mainButton.setOnClickListener {
            // Переход на главную страницу
            val intent = Intent(this, MainPageActivity::class.java)
            startActivity(intent)
        }

        profileButton.setOnClickListener {
            // Переход на страницу профиля
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        saveButton.setOnClickListener {
            // Логика сохранения данных
            Toast.makeText(this, "Данные сохранены", Toast.LENGTH_SHORT).show()
        }

        backButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        deleteButton.setOnClickListener {
            // Логика удаления аккаунта
            Toast.makeText(this, "Аккаунт удалён", Toast.LENGTH_SHORT).show()
        }
    }
}