package com.kaplich.myhealth.addendum

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kaplich.myhealth.R
import com.kaplich.myhealth.database.DatabaseHelper
import com.kaplich.myhealth.pages.LoginActivity
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

    private lateinit var dbHelper: DatabaseHelper
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        dbHelper = DatabaseHelper(this)
        userId = intent.getIntExtra("USER_ID", 0)

        initializeViews()
        setupListeners()
        loadUserInfo()
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
    }

    private fun loadUserInfo() {
        val user = dbHelper.getUser(userId)
        if (user != null) {
            loginTextView.text = "ID: ${user.id}" // показываем реальный ID
            newNameEditText.setText(user.name)
        } else {
            Toast.makeText(this, "Пользователь не найден", Toast.LENGTH_SHORT).show()
        }
    }


    private fun setupListeners() {
        mainButton.setOnClickListener {
            val intent = Intent(this, MainPageActivity::class.java)
            intent.putExtra("USER_ID", userId) // Передаем userId
            startActivity(intent)
        }

        profileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("USER_ID", userId) // Передаем userId
            startActivity(intent)
        }

        saveButton.setOnClickListener {
            updateUserInfo()
        }

        backButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("USER_ID", userId) // Передаем userId
            startActivity(intent)
        }

        deleteButton.setOnClickListener {
            deleteUser()
        }
    }


    private fun updateUserInfo() {
        val newName = newNameEditText.text.toString().trim()
        val oldPassword = oldPasswordEditText.text.toString().trim()
        val newPassword = newPasswordEditText.text.toString().trim()
        val newPasswordRepeat = newPasswordRepeatEditText.text.toString().trim()

        // Если оба поля пустые, просим заполнить хотя бы одно
        if (newName.isEmpty() && oldPassword.isEmpty() && newPassword.isEmpty() && newPasswordRepeat.isEmpty()) {
            Toast.makeText(this, "Заполните хотя бы одно поле для изменения", Toast.LENGTH_SHORT).show()
            return
        }

        // Проверка на совпадение паролей, если только пароль должен быть обновлен
        if (newPassword.isNotEmpty() && newPassword != newPasswordRepeat) {
            Toast.makeText(this, "Новые пароли не совпадают", Toast.LENGTH_SHORT).show()
            return
        }

        // Если требуется обновление пароля, проверяем старый пароль
        val user = dbHelper.getUser(userId)
        if (newPassword.isNotEmpty() && (user == null || user.password != oldPassword)) {
            Toast.makeText(this, "Неправильный старый пароль", Toast.LENGTH_SHORT).show()
            return
        }

        // Обновляем только заполненные поля
        val updateName = newName.isNotEmpty()
        val updatePassword = newPassword.isNotEmpty()

        if (dbHelper.updateUserInfo(userId, if (updateName) newName else null, if (updatePassword) newPassword else null)) {
            Toast.makeText(this, "Данные обновлены", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("USER_ID", userId) // Передаем userId
            startActivity(intent)
        } else {
            Toast.makeText(this, "Ошибка при обновлении данных", Toast.LENGTH_SHORT).show()
        }
    }


    private fun deleteUser() {
        if (dbHelper.deleteUser(userId)) {
            Toast.makeText(this, "Аккаунт удалён", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "Ошибка при удалении аккаунта", Toast.LENGTH_SHORT).show()
        }
    }
}
