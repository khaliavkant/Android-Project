package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

// Адаптер приймає список рядків (завдань)
class TaskAdapter(private val taskList: MutableList<String>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    // Клас, який "тримає" елементи дизайну одного рядка
    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTaskName: TextView = itemView.findViewById(R.id.tvTaskName)
        val btnDeleteTask: ImageButton = itemView.findViewById(R.id.btnDeleteTask)
    }

    // Створюємо новий рядок (викликається, коли на екрані з'являється нове місце)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    // Заповнюємо рядок даними (викликається для кожного завдання)
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.tvTaskName.text = task

        // Видалення (цей код у вас вже є)
        holder.btnDeleteTask.setOnClickListener {
            val currentPos = holder.adapterPosition
            if (currentPos != RecyclerView.NO_POSITION) {
                taskList.removeAt(currentPos)
                notifyItemRemoved(currentPos)
                notifyItemRangeChanged(currentPos, taskList.size)
            }
        }

        // НОВЕ: Редагування при натисканні на текст
        holder.tvTaskName.setOnClickListener {
            val context = holder.itemView.context
            val builder = android.app.AlertDialog.Builder(context)
            builder.setTitle("Редагувати завдання")

            val input = android.widget.EditText(context)
            input.setText(task) // Вставляємо старий текст, щоб було зручно міняти
            builder.setView(input)

            builder.setPositiveButton("Зберегти") { dialog, _ ->
                val updatedTask = input.text.toString()
                val currentPos = holder.adapterPosition
                if (updatedTask.isNotEmpty() && currentPos != RecyclerView.NO_POSITION) {
                    // Оновлюємо текст у списку
                    taskList[currentPos] = updatedTask
                    // Кажемо адаптеру оновити цей конкретний рядок на екрані
                    notifyItemChanged(currentPos)
                }
            }

            builder.setNegativeButton("Скасувати") { dialog, _ -> dialog.cancel() }
            builder.show()
        }
    }

    // Повертає загальну кількість завдань
    override fun getItemCount(): Int {
        return taskList.size
    }
}