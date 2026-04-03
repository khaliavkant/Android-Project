package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TasksActivity : AppCompatActivity() {

    // Створюємо стартовий список з 10 елементів
    private val taskList = mutableListOf(
        "Купити молоко та хліб",
        "Підготуватися до пари з програмування",
        "Подзвонити мамі",
        "Зробити домашку з Android",
        "Полити квіти",
        "Винести сміття",
        "Прочитати новий розділ книги",
        "Записатися до лікаря",
        "Приготувати смачну вечерю",
        "Лягти спати до півночі"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        val rvTasks = findViewById<RecyclerView>(R.id.rvTasks)
        rvTasks.layoutManager = LinearLayoutManager(this)

        val adapter = TaskAdapter(taskList)
        rvTasks.adapter = adapter

        // Знаходимо нашу кнопку "+"
        val fabAddTask = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fabAddTask)

        // Вішаємо слухач натискань
        fabAddTask.setOnClickListener {
            // Створюємо спливаюче вікно
            val builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("Нове завдання")

            // Створюємо поле для вводу тексту
            val input = android.widget.EditText(this)
            input.hint = "Що плануєте зробити?"
            builder.setView(input)

            // Кнопка "Додати"
            builder.setPositiveButton("Додати") { dialog, _ ->
                val newTask = input.text.toString()
                if (newTask.isNotEmpty()) {
                    // Додаємо в наш список
                    taskList.add(newTask)
                    // Кажемо адаптеру, що з'явився новий елемент у кінці
                    adapter.notifyItemInserted(taskList.size - 1)
                    // Прокручуємо список до самого низу, щоб побачити нове завдання
                    rvTasks.scrollToPosition(taskList.size - 1)
                }
            }

            // Кнопка "Скасувати"
            builder.setNegativeButton("Скасувати") { dialog, _ ->
                dialog.cancel()
            }

            // Показуємо вікно
            builder.show()
        }
    }
}
