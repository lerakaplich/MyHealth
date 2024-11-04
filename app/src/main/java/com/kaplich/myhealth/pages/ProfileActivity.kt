package com.kaplich.myhealth.pages

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.content.Intent
import android.util.Log
import android.view.MotionEvent
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.kaplich.myhealth.R
import com.kaplich.myhealth.database.User
import com.kaplich.myhealth.addendum.AddAnalysisActivity
import com.kaplich.myhealth.addendum.AddAppointmentActivity
import com.kaplich.myhealth.addendum.ProfileEditActivity
import com.kaplich.myhealth.addendum.AddMedicationActivity
import com.kaplich.myhealth.database.DatabaseHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileActivity : AppCompatActivity() {

    private lateinit var pillsButton: Button
    private lateinit var medCardButton: Button
    private lateinit var exitButton: Button
    private lateinit var profileButton: Button
    private lateinit var appointmentButton: Button
    private lateinit var mainButton: Button
    private lateinit var editProfileButton: Button
    private lateinit var nameTextView: TextView
    private lateinit var loginTextView: TextView
    private var startX: Float = 0f
    private var endX: Float = 0f
    private val SWIPE_THRESHOLD = 100
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        userId = intent.getIntExtra("USER_ID", 0)

        if (userId <= 0) {
            // Если userId некорректен, закрываем активность
            finish()
            return
        }

        initializeViews()
        setupListeners()
        fetchUserData(userId)
    }

    private fun fetchUserData(userId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val dbHelper = DatabaseHelper(applicationContext)
            val user = dbHelper.getUserById(userId) // Измените вызов на getUserById

            withContext(Dispatchers.Main) {
                if (user != null) {
                    updateUI(user)
                } else {
                    Log.e("ProfileActivity", "User not found for userId: $userId")
                    nameTextView.text = "Пользователь не найден"
                    loginTextView.text = "ID: $userId"
                }
            }
        }
    }


    private fun updateUI(user: User) {
        // Обновляем TextView с именем и ID пользователя
        nameTextView.text = user.name
        loginTextView.text = "ID: ${user.id}"
    }

    private fun initializeViews() {
        pillsButton = findViewById(R.id.addMedButton)
        medCardButton = findViewById(R.id.addAnalyzesButton)
        exitButton = findViewById(R.id.saveButton)
        profileButton = findViewById(R.id.profileButton)
        nameTextView = findViewById(R.id.name)
        loginTextView = findViewById(R.id.login)
        editProfileButton = findViewById(R.id.editProfileButton)
        mainButton = findViewById(R.id.mainButton)
        appointmentButton = findViewById(R.id.addAppointmentButton)
    }

    private fun setupListeners() {
        exitButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        appointmentButton.setOnClickListener {
            val intent = Intent(this, AddAppointmentActivity::class.java).apply {
                putExtra("USER_ID", userId)
            }
            startActivity(intent)
            finish()
        }

        medCardButton.setOnClickListener {
            val intent = Intent(this, AddAnalysisActivity::class.java).apply {
                putExtra("USER_ID", userId)
            }
            startActivity(intent)
            finish()
        }

        editProfileButton.setOnClickListener {
            val intent = Intent(this, ProfileEditActivity::class.java).apply {
                putExtra("USER_ID", userId)
            }
            startActivity(intent)
            finish()
        }

        pillsButton.setOnClickListener {
            val intent = Intent(this, AddMedicationActivity::class.java).apply {
                putExtra("USER_ID", userId)
            }
            startActivity(intent)
            finish()
        }

        mainButton.setOnClickListener {
            val intent = Intent(this, MainPageActivity::class.java).apply {
                putExtra("USER_ID", userId)
            }
            startActivity(intent)
            finish()
        }

        profileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java).apply {
                putExtra("USER_ID", userId)
            }
            startActivity(intent)
            finish()
        }

        window.decorView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = event.x
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    endX = event.x
                    true
                }
                MotionEvent.ACTION_UP -> {
                    endX = event.x
                    when {
                        endX > startX + SWIPE_THRESHOLD -> swipeRight()
                        startX > endX + SWIPE_THRESHOLD -> swipeLeft()
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun swipeRight() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.swipe_right)
        window.decorView.startAnimation(animation)

        val mainPageIntent = Intent(this, MainPageActivity::class.java)
        startActivity(mainPageIntent)
        finish()
    }

    private fun swipeLeft() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.swipe_left)
        window.decorView.startAnimation(animation)

        finish() // Закрываем текущую активность
    }
}
