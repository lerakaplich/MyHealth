package com.kaplich.myhealth.addendum

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kaplich.myhealth.R
import com.kaplich.myhealth.pages.MainPageActivity
import com.kaplich.myhealth.pages.ProfileActivity



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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_medication)

        initializeViews()
        setupListeners()
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

    private fun setupListeners() {
        saveButton.setOnClickListener {
            // Логика сохранения данных
            Toast.makeText(this, "Лекарство сохранено", Toast.LENGTH_SHORT).show()
        }

        backButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

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
    }
}