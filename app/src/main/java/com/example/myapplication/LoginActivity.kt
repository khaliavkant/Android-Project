package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etLogin = findViewById<EditText>(R.id.etLoginAuth)
        val etPassword = findViewById<EditText>(R.id.etPasswordAuth)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvGoToRegister = findViewById<TextView>(R.id.tvGoToRegister)

        val sharedPreferences = getSharedPreferences("TaskAppPrefs", Context.MODE_PRIVATE)

        btnLogin.setOnClickListener {
            val inputLogin = etLogin.text.toString()
            val inputPassword = etPassword.text.toString()

            // Отримуємо збережені дані
            val savedLogin = sharedPreferences.getString("login", "")
            val savedPassword = sharedPreferences.getString("password", "")

            // Порівнюємо
            if (inputLogin == savedLogin && inputPassword == savedPassword && savedLogin!!.isNotEmpty()) {
                // Якщо вхід успішний, зберігаємо прапорець
                sharedPreferences.edit().putBoolean("isAuthorized", true).apply()

                startActivity(Intent(this, MenuActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Невірний логін або пароль", Toast.LENGTH_SHORT).show()
            }
        }

        // Перехід на екран реєстрації
        tvGoToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}