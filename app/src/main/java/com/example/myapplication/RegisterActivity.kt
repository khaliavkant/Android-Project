package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register) // Тут має бути ваш XML файл дизайну

        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etLogin = findViewById<EditText>(R.id.etLogin)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etPasswordConfirm = findViewById<EditText>(R.id.etPasswordConfirm)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val login = etLogin.text.toString()
            val password = etPassword.text.toString()
            val passwordConfirm = etPasswordConfirm.text.toString()

            // Валідація
            if (name.isEmpty() || email.isEmpty() || login.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Заповніть всі поля!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Неправильний формат Email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(this, "Пароль має бути не менше 6 символів", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != passwordConfirm) {
                Toast.makeText(this, "Паролі не співпадають", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Збереження в SharedPreferences
            val sharedPreferences = getSharedPreferences("TaskAppPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            editor.putString("name", name)
            editor.putString("email", email)
            editor.putString("login", login)
            editor.putString("password", password)
            editor.apply()

            Toast.makeText(this, "Реєстрація успішна!", Toast.LENGTH_SHORT).show()
            finish() // Повертаємося на екран логіну
        }
    }
}