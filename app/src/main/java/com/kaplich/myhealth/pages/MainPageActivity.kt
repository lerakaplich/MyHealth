package com.kaplich.myhealth.pages

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ExpandableListView
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import com.kaplich.myhealth.R
import com.kaplich.myhealth.adapters.ExpandableListAdapter
import com.kaplich.myhealth.addendum.AddAnalysisActivity
import com.kaplich.myhealth.addendum.AddAppointmentActivity
import com.kaplich.myhealth.addendum.AddMedicationActivity
import com.kaplich.myhealth.database.DatabaseHelper

class MainPageActivity : AppCompatActivity() {

    private lateinit var expandableListView: ExpandableListView
    private lateinit var mainButton: Button
    private lateinit var profileButton: Button
    private lateinit var databaseHelper: DatabaseHelper
    private var userId: Int = 0 // Для хранения userId

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация DatabaseHelper
        databaseHelper = DatabaseHelper(this)

        // Получение userId из Intent
        userId = intent.getIntExtra("USER_ID", 0)

        initializeViews()
        setupExpandableListView()
        setupListeners()
    }

    private fun initializeViews() {
        mainButton = findViewById(R.id.mainButton)
        profileButton = findViewById(R.id.profileButton)
        expandableListView = findViewById(R.id.expandableListView)
        // Обработчик нажатия на FAB
        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener { showPopupMenu(fab) }
    }

    private fun showPopupMenu(view: View) {
        // Создаем PopupMenu
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)

        // Обработчик выбора элемента меню
        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.add_analysis  -> {
                    val intent = Intent(this, AddAnalysisActivity::class.java).apply {
                        putExtra("USER_ID", userId)
                    }
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.add_medication -> {
                    val intent = Intent(this, AddMedicationActivity::class.java).apply {
                        putExtra("USER_ID", userId)
                    }
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.add_appointment -> {
                    val intent = Intent(this, AddAppointmentActivity::class.java).apply {
                        putExtra("USER_ID", userId)
                    }
                    startActivity(intent)
                    finish()
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }


    @SuppressLint("Range")
    private fun setupExpandableListView() {
        val listData = hashMapOf<String, List<String>>()

        // Получение данных из базы
        val medicationsCursor = databaseHelper.getMedications(userId)
        val analysesCursor = databaseHelper.getAnalyses(userId)
        val appointmentsCursor = databaseHelper.getAppointments(userId)

        // Лекарства
        val medications = mutableListOf<String>()
        while (medicationsCursor.moveToNext()) {
            val name = medicationsCursor.getString(medicationsCursor.getColumnIndex("name"))
            val type = medicationsCursor.getString(medicationsCursor.getColumnIndex("type"))
            val frequency = medicationsCursor.getString(medicationsCursor.getColumnIndex("frequency"))
            val startDate = medicationsCursor.getString(medicationsCursor.getColumnIndex("startDate"))
            val endDate = medicationsCursor.getString(medicationsCursor.getColumnIndex("endDate"))

            // Форматируем строку с информацией о лекарствах
            val medicationInfo = "Название: $name\nВид: $type\nЧастота приема: $frequency\nПериод приема: $startDate - $endDate"
            medications.add(medicationInfo)
        }
        medicationsCursor.close()

        // Анализы
        val analyses = mutableListOf<String>()
        while (analysesCursor.moveToNext()) {
            val analysisDate = analysesCursor.getString(analysesCursor.getColumnIndex("analysisDate"))
            val comment = analysesCursor.getString(analysesCursor.getColumnIndex("comment")) // Исправлено на "comment"

            // Форматируем строку с информацией о анализах
            val analysisInfo = "Дата: $analysisDate\nКомментарий: $comment"
            analyses.add(analysisInfo)
        }
        analysesCursor.close()

        // Записи к врачу
        val appointments = mutableListOf<String>()
        while (appointmentsCursor.moveToNext()) {
            val specialization = appointmentsCursor.getString(appointmentsCursor.getColumnIndex("doctorSpecialization"))
            val appointmentDate = appointmentsCursor.getString(appointmentsCursor.getColumnIndex("appointmentDate"))
            val comment = appointmentsCursor.getString(appointmentsCursor.getColumnIndex("comment")) // Исправлено на "comment"

            // Форматируем строку с информацией о записи к врачу
            val appointmentInfo = "Специализация: $specialization\nДата записи: $appointmentDate\nКомментарий: $comment"
            appointments.add(appointmentInfo)
        }
        appointmentsCursor.close()

        // Добавляем данные в список
        listData["Лекарства"] = medications
        listData["Анализы"] = analyses
        listData["Запись к врачу"] = appointments

        // Лог для отладки
        Log.d("MainPageActivity", "Данные для ExpandableListView: $listData")

        val adapter = ExpandableListAdapter(this, listData)
        expandableListView.setAdapter(adapter)
    }





    private fun setupListeners() {
        mainButton.setOnClickListener {
            // Здесь можно оставить как есть, или сделать переход на MainPageActivity без необходимости создания нового Intent
            // Если это необходимо, вы можете передать userId:
            val intent = Intent(this, MainPageActivity::class.java)
            intent.putExtra("USER_ID", userId) // Передаем userId
            startActivity(intent)
        }

        profileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("USER_ID", userId) // Передаем userId
            startActivity(intent)
        }
    }
}
