package com.kaplich.myhealth.addendum

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kaplich.myhealth.R
import com.kaplich.myhealth.pages.MainPageActivity
import com.kaplich.myhealth.pages.ProfileActivity

class AddAnalysisActivity : AppCompatActivity() {

    private lateinit var saveButton: Button
    private lateinit var backButton: Button
    private lateinit var mainButton: Button
    private lateinit var profileButton: Button
    private lateinit var placeEditText: EditText
    private lateinit var dateEditText: EditText
    private lateinit var commentEditText: EditText
    private lateinit var titleTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_analysis)

        initializeViews()
        setupListeners()
    }

    private fun initializeViews() {
        saveButton = findViewById(R.id.saveButton)
        backButton = findViewById(R.id.backButton)
        mainButton = findViewById(R.id.mainButton)
        profileButton = findViewById(R.id.profileButton)
        dateEditText = findViewById(R.id.dateAnalysisEnter)
        commentEditText = findViewById(R.id.analysisCommentEnter)
        titleTextView = findViewById(R.id.addText)
    }

    private fun setupListeners() {
        saveButton.setOnClickListener {
            // Логика сохранения данных
            Toast.makeText(this, "Данные сохранены", Toast.LENGTH_SHORT).show()
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