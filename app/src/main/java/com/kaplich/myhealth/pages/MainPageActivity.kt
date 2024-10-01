package com.kaplich.myhealth.pages

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ExpandableListView
import androidx.appcompat.app.AppCompatActivity
import com.kaplich.myhealth.R
import com.kaplich.myhealth.adapters.ExpandableListAdapter

class MainPageActivity : AppCompatActivity() {
    private lateinit var expandableListView: ExpandableListView
    private lateinit var listDataHeader: List<String>
    private lateinit var listDataChild: HashMap<String, List<String>>
    private lateinit var mainButton: Button
    private lateinit var profileButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeViews();
        prepareListData()
        val listAdapter = ExpandableListAdapter(this, listDataHeader, listDataChild)
        expandableListView.setAdapter(listAdapter)
        setupListeners();
    }

    private fun initializeViews() {
        mainButton = findViewById(R.id.mainButton)
        profileButton = findViewById(R.id.profileButton)
        expandableListView = findViewById(R.id.expandableListView)
    }
    private fun prepareListData() {
        // Заголовки группы
        listDataHeader = listOf("Лекарства", "Запись к врачу", "Анализы")
        listDataChild = HashMap()

        // Данные для каждой группы
        val medicines = listOf(
            "Ибупрофен\nВид: таблетка\nЧастота: раз в день\nПериод: 29.07.2024-29.10.2024",
            "Тотема\nВид: ампула\nЧастота: два раза в сутки\nПериод: 09.09.2024-29.09.2024"
        )

        val appointments = listOf(
            "Терапевт\nДата: 19.10.2024\nКомментарий: пить ибупрофен раз в день"
        )

        val tests = listOf(
            "Фото результатов анализов\nДата: 19.10.2024\nКомментарий: низкий гемоглобин"
        )

        // Привязка данных к заголовкам
        listDataChild[listDataHeader[0]] = medicines // Лекарства
        listDataChild[listDataHeader[1]] = appointments // Запись к врачу
        listDataChild[listDataHeader[2]] = tests // Анализы
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
    }
}