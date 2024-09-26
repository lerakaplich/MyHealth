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

class AddEventActivity : AppCompatActivity() {

    private lateinit var saveButton: Button
    private lateinit var backButton: Button
    private lateinit var mainButton: Button
    private lateinit var profileButton: Button
    private lateinit var eventEditText: EditText
    private lateinit var dateEditText: EditText
    private lateinit var eventSpinner: Spinner
    private lateinit var titleTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)

        initializeViews()
        setupListeners()
    }

    private fun initializeViews() {
        saveButton = findViewById(R.id.saveButton)
        backButton = findViewById(R.id.backButton)
        mainButton = findViewById(R.id.mainButton)
        profileButton = findViewById(R.id.profileButton)
        eventEditText = findViewById(R.id.eventEnter)
        dateEditText = findViewById(R.id.dateEventEnter)
        eventSpinner = findViewById(R.id.eventSpinner)
        titleTextView = findViewById(R.id.addText)
    }

    private fun setupListeners() {
        saveButton.setOnClickListener {
            // Логика сохранения данных
            Toast.makeText(this, "Событие сохранено", Toast.LENGTH_SHORT).show()
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