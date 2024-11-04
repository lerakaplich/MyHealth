package com.kaplich.myhealth.addendum

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kaplich.myhealth.R
import com.kaplich.myhealth.database.DatabaseHelper
import com.kaplich.myhealth.pages.MainPageActivity
import com.kaplich.myhealth.pages.ProfileActivity

class AddAnalysisActivity : AppCompatActivity() {

    private lateinit var saveButton: Button
    private lateinit var backButton: Button
    private lateinit var mainButton: Button
    private lateinit var profileButton: Button
    private lateinit var dateEditText: EditText
    private lateinit var commentEditText: EditText
    private lateinit var titleTextView: TextView
    private lateinit var dbHelper: DatabaseHelper
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_analysis)

        // Инициализация DatabaseHelper
        dbHelper = DatabaseHelper(this)

        // Получение userId из Intent
        userId = intent.getIntExtra("USER_ID", 0)

        initializeViews()
        setupListeners()
        setupDateInput()
    }

    private fun initializeViews() {
        saveButton = findViewById(R.id.saveButton)
        backButton = findViewById(R.id.backButton)
        mainButton = findViewById(R.id.mainButton)
        profileButton = findViewById(R.id.profileButton)
        dateEditText = findViewById(R.id.dataAnalysisEnter)
        commentEditText = findViewById(R.id.analysisCommentEnter)
        titleTextView = findViewById(R.id.addText)
    }

    private fun setupListeners() {
        saveButton.setOnClickListener {
            val analysisDate = dateEditText.text.toString().trim()
            val comment = commentEditText.text.toString().trim()

            // Валидация данных
            if (analysisDate.isNotEmpty()) {
                val result = dbHelper.addAnalysis(userId, analysisDate, comment)
                if (result != -1L) {
                    Toast.makeText(this, "Анализ сохранен", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, ProfileActivity::class.java)
                    intent.putExtra("USER_ID", userId) // Передаем userId
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Ошибка при сохранении", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Пожалуйста, заполните дату анализа", Toast.LENGTH_SHORT).show()
            }
        }

        backButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("USER_ID", userId) // Передаем userId
            startActivity(intent)
        }

        mainButton.setOnClickListener {
            val intent = Intent(this, MainPageActivity::class.java)
            intent.putExtra("USER_ID", userId) // Если хотите передать userId и на главную
            startActivity(intent)
        }

        profileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("USER_ID", userId) // Передаем userId
            startActivity(intent)
        }
    }

    private fun setupDateInput() {
        dateEditText.addTextChangedListener(object : TextWatcher {
            private var isFormatting = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isFormatting) return

                isFormatting = true

                // Удаляем все нецифровые символы
                val cleaned = s.toString().replace(Regex("[^\\d]"), "")

                // Форматируем в дд.мм.гггг
                val formatted = when {
                    cleaned.length <= 2 -> cleaned
                    cleaned.length <= 4 -> "${cleaned.substring(0, 2)}.${cleaned.substring(2)}"
                    cleaned.length <= 8 -> "${cleaned.substring(0, 2)}.${cleaned.substring(2, 4)}.${cleaned.substring(4)}"
                    else -> "${cleaned.substring(0, 2)}.${cleaned.substring(2, 4)}.${cleaned.substring(4, 8)}"
                }

                // Устанавливаем форматированный текст
                dateEditText.setText(formatted)
                dateEditText.setSelection(formatted.length) // Устанавливаем курсор в конец текста

                isFormatting = false
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}
