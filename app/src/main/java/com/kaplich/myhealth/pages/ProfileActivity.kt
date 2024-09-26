package com.kaplich.myhealth.pages

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.kaplich.myhealth.R
import com.kaplich.myhealth.addendum.AddAnalysisActivity
import com.kaplich.myhealth.addendum.AddDoctorActivity
import com.kaplich.myhealth.addendum.AddEventActivity
import com.kaplich.myhealth.addendum.ProfileEditActivity
import com.kaplich.myhealth.addendum.AddMedicationActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var pillsButton: Button
    private lateinit var medCardButton: Button
    private lateinit var exitButton: Button
    private lateinit var profileButton: Button
    private lateinit var mainButton: Button
    private lateinit var editProfileButton: Button
    private lateinit var backButton: Button
    private lateinit var nameTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        initializeViews()

        setupListeners()
    }

    private fun initializeViews() {
        pillsButton = findViewById(R.id.addMedButton)
        medCardButton = findViewById(R.id.addAnalyzesButton)
        exitButton = findViewById(R.id.saveButton)
        profileButton = findViewById(R.id.profileButton)
        nameTextView = findViewById(R.id.name)
        editProfileButton = findViewById(R.id.editProfileButton)
        mainButton = findViewById(R.id.mainButton)
    }


    private fun setupListeners() {
        exitButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        medCardButton.setOnClickListener {
            val intent = Intent(this, AddAnalysisActivity::class.java)
            startActivity(intent)
            finish()
        }

        editProfileButton.setOnClickListener {
            val intent = Intent(this, ProfileEditActivity::class.java)
            startActivity(intent)
            finish()
        }

        pillsButton.setOnClickListener{
            val intent = Intent(this, AddMedicationActivity::class.java)
            startActivity(intent)
            finish()
        }
        mainButton.setOnClickListener{
            val intent = Intent(this, MainPageActivity::class.java)
            startActivity(intent)
            finish()
        }
        profileButton.setOnClickListener{
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

    }


}
