package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Context
import android.content.Intent

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Отримуємо доступ до SharedPreferences
        val sharedPreferences = getSharedPreferences("TaskAppPrefs", Context.MODE_PRIVATE)

        // Перевіряємо прапорець isAuthorized (за замовчуванням false) [cite: 22, 24]
        val isAuthorized = sharedPreferences.getBoolean("isAuthorized", false)

        // Маршрутизація [cite: 25]
        if (isAuthorized) {
            // Якщо авторизований -> йдемо в головне меню [cite: 25]
            startActivity(Intent(this, MenuActivity::class.java))
        } else {
            // Якщо ні -> йдемо на екран входу [cite: 25]
            startActivity(Intent(this, LoginActivity::class.java))
        }

        // Закриваємо SplashActivity, щоб користувач не міг повернутися сюди кнопкою "Назад"
        finish()
    }
}