package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TasksActivity : AppCompatActivity() {

    // Наш стартовий список
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

        // Відновлена логіка кнопки "+"
        val fabAddTask = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fabAddTask)

        fabAddTask.setOnClickListener {
            val builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("Нове завдання")

            val input = android.widget.EditText(this)
            input.hint = "Що плануєте зробити?"
            builder.setView(input)

            builder.setPositiveButton("Додати") { dialog, _ ->
                val newTask = input.text.toString()
                if (newTask.isNotEmpty()) {
                    taskList.add(newTask) // Додаємо в пам'ять
                    adapter.notifyItemInserted(taskList.size - 1) // Оновлюємо список
                    rvTasks.scrollToPosition(taskList.size - 1) // Гортаємо вниз
                }
            }

            builder.setNegativeButton("Скасувати") { dialog, _ -> dialog.cancel() }
            builder.show()
        }
    }
}