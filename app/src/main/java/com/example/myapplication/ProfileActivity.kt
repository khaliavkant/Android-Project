package com.example.myapplication

import android.app.DatePickerDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val etProfileName = findViewById<TextInputEditText>(R.id.etProfileName)
        val etProfileEmail = findViewById<TextInputEditText>(R.id.etProfileEmail)
        val etProfileDob = findViewById<TextInputEditText>(R.id.etProfileDob)
        val btnSaveProfile = findViewById<Button>(R.id.btnSaveProfile)

        // Знаходимо наше фото та кнопку зміни
        val ivAvatar = findViewById<ImageView>(R.id.ivAvatar)
        val btnChangePhoto = findViewById<Button>(R.id.btnChangePhoto)

        val sharedPreferences = getSharedPreferences("TaskAppPrefs", Context.MODE_PRIVATE)

        // 1. Підставляємо збережені тексти
        etProfileName.setText(sharedPreferences.getString("name", ""))
        etProfileEmail.setText(sharedPreferences.getString("email", ""))
        etProfileDob.setText(sharedPreferences.getString("dob", ""))

        // 2. Підставляємо збережене фото (якщо воно є)
        val savedImageUri = sharedPreferences.getString("avatarUri", null)
        if (savedImageUri != null) {
            try {
                ivAvatar.setImageURI(Uri.parse(savedImageUri))
            } catch (e: Exception) {
                // Якщо фото видалили з телефону, ігноруємо помилку
            }
        }

        // 3. Створюємо інструмент для відкриття галереї
        val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                // Коли користувач обрав фото, ставимо його на екран
                ivAvatar.setImageURI(uri)
                // Зберігаємо шлях до цього фото в пам'ять
                sharedPreferences.edit().putString("avatarUri", uri.toString()).apply()
            }
        }

        // 4. Що відбувається при натисканні на "Змінити фото"
        btnChangePhoto.setOnClickListener {
            // Запускаємо галерею, просимо показати тільки картинки
            pickImageLauncher.launch("image/*")
        }

        // 5. Календар
        etProfileDob.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    etProfileDob.setText(formattedDate)
                }, year, month, day)
            datePickerDialog.show()
        }

        // 6. Збереження тексту
        btnSaveProfile.setOnClickListener {
            val newName = etProfileName.text.toString()
            val newEmail = etProfileEmail.text.toString()
            val newDob = etProfileDob.text.toString()

            if (newName.isNotEmpty() && newEmail.isNotEmpty()) {
                sharedPreferences.edit().apply {
                    putString("name", newName)
                    putString("email", newEmail)
                    putString("dob", newDob)
                    apply()
                }
                Toast.makeText(this, "Профіль успішно збережено! ✅", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Ім'я та Email не можуть бути порожніми", Toast.LENGTH_SHORT).show()
            }
        }
    }
}