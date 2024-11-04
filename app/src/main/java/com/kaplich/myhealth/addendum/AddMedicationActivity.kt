package com.kaplich.myhealth.addendum

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kaplich.myhealth.R
import com.kaplich.myhealth.database.DatabaseHelper
import com.kaplich.myhealth.database.Medication
import com.kaplich.myhealth.pages.MainPageActivity
import com.kaplich.myhealth.pages.ProfileActivity
import java.util.Calendar

class AddMedicationActivity : AppCompatActivity() {

    private lateinit var saveButton: Button
    private lateinit var backButton: Button
    private lateinit var mainButton: Button
    private lateinit var profileButton: Button
    private lateinit var medNameEditText: EditText
    private lateinit var medStartDateEditText: EditText
    private lateinit var medEndDateEditText: EditText
    private lateinit var medTypeSpinner: Spinner
    private lateinit var periodMedSpinner: Spinner
    private lateinit var titleTextView: TextView
    private lateinit var dbHelper: DatabaseHelper
    private var userId: Int = 0 // Идентификатор пользователя

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_medication)

        dbHelper = DatabaseHelper(this)

        userId = intent.getIntExtra("USER_ID", 0)

        initializeViews()
        setupSpinners()
        setupListeners()
        setupDateInputs()
    }

    private fun initializeViews() {
        saveButton = findViewById(R.id.saveButton)
        backButton = findViewById(R.id.backButton)
        mainButton = findViewById(R.id.mainButton)
        profileButton = findViewById(R.id.profileButton)
        medNameEditText = findViewById(R.id.medEnter)
        medStartDateEditText = findViewById(R.id.medStartDate)
        medEndDateEditText = findViewById(R.id.medEndDate)
        medTypeSpinner = findViewById(R.id.medTypeSpinner)
        periodMedSpinner = findViewById(R.id.periodMedSpinner)
        titleTextView = findViewById(R.id.addText)
    }

    private fun setupSpinners() {
        val medTypes = arrayOf("Таблетка", "Ампула", "Капли", "Сироп")
        val frequencies = arrayOf("3 раза в день", "2 раза в день", "1 раз в день", "Раз в неделю")

        val medTypeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, medTypes)
        medTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        medTypeSpinner.adapter = medTypeAdapter

        val frequencyAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, frequencies)
        frequencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        periodMedSpinner.adapter = frequencyAdapter
    }

    private fun setupListeners() {
        saveButton.setOnClickListener {
            val medName = medNameEditText.text.toString().trim()
            val startDate = medStartDateEditText.text.toString().trim()
            val endDate = medEndDateEditText.text.toString().trim()
            val medType = medTypeSpinner.selectedItem.toString()
            val frequency = periodMedSpinner.selectedItem.toString()

            Log.d("AddMedicationActivity", "User ID: $userId")

            if (medName.isNotEmpty() && startDate.isNotEmpty() && endDate.isNotEmpty()) {
                val medication = Medication(0, userId, medName, frequency, medType, startDate, endDate)
                val result = dbHelper.addMedication(medication)
                if (result != -1L) {
                    Toast.makeText(this, "Лекарство сохранено", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, ProfileActivity::class.java)
                    intent.putExtra("USER_ID", userId)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Ошибка при сохранении", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }

        backButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("USER_ID", userId)
            startActivity(intent)
            finish()
        }

        mainButton.setOnClickListener {
            val intent = Intent(this, MainPageActivity::class.java)
            intent.putExtra("USER_ID", userId)
            startActivity(intent)
            finish()
        }

        profileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("USER_ID", userId)
            startActivity(intent)
            finish()
        }
    }

    private fun setupDateInputs() {
        // Настройка выбора даты для начальной и конечной дат
        medStartDateEditText.setOnClickListener {
            showDatePickerDialog(medStartDateEditText)
        }
        medEndDateEditText.setOnClickListener {
            showDatePickerDialog(medEndDateEditText)
        }

        // Добавляем TextWatcher для форматирования даты
        setupDateInput(medStartDateEditText)
        setupDateInput(medEndDateEditText)
    }

    // Пример диалога выбора даты
    private fun showDatePickerDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val formattedDate = String.format("%02d.%02d.%04d", selectedDay, selectedMonth + 1, selectedYear)
            editText.setText(formattedDate)
        }, year, month, day)

        datePickerDialog.show()
    }


    // Пример диалога выбора даты


    private fun setupDateInput(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
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
                editText.setText(formatted)
                editText.setSelection(formatted.length) // Устанавливаем курсор в конец текста

                isFormatting = false
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}
