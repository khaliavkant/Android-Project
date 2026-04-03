package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Знаходимо всі елементи на екрані за їх ID
        val tvGreeting = findViewById<TextView>(R.id.tvGreeting)
        val btnTasks = findViewById<Button>(R.id.btnTasks)
        val btnCategories = findViewById<Button>(R.id.btnCategories)
        val btnProfile = findViewById<Button>(R.id.btnProfile)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        // Отримуємо збережене ім'я з SharedPreferences
        val sharedPreferences = getSharedPreferences("TaskAppPrefs", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("name", "Користувач")

        // Встановлюємо персональне вітання
        tvGreeting.text = "Привіт, $userName! 👋"

        // Логіка для кнопки "Вийти"
        btnLogout.setOnClickListener {
            // Змінюємо прапорець isAuthorized на false
            sharedPreferences.edit().putBoolean("isAuthorized", false).apply()

            // Перекидаємо користувача назад на екран входу
            startActivity(Intent(this, LoginActivity::class.java))

            // Закриваємо MenuActivity, щоб не можна було повернутися кнопкою "Назад"
            finish()
        }

        btnTasks.setOnClickListener {
            startActivity(Intent(this, TasksActivity::class.java))
        }

        btnCategories.setOnClickListener {
            Toast.makeText(this, "Тут будуть категорії!", Toast.LENGTH_SHORT).show()
        }

        btnProfile.setOnClickListener {
            Toast.makeText(this, "Тут будуть налаштування профілю!", Toast.LENGTH_SHORT).show()
        }
    }
}