package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private val taskList: MutableList<String>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTaskName: TextView = itemView.findViewById(R.id.tvTaskName)
        val btnDeleteTask: ImageButton = itemView.findViewById(R.id.btnDeleteTask)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.tvTaskName.text = task

        // Видалення завдання (Смітник)
        holder.btnDeleteTask.setOnClickListener {
            val currentPos = holder.adapterPosition
            if (currentPos != RecyclerView.NO_POSITION) {
                taskList.removeAt(currentPos)
                notifyItemRemoved(currentPos)
                notifyItemRangeChanged(currentPos, taskList.size)
            }
        }

        // Редагування завдання (Натискання на текст)
        holder.tvTaskName.setOnClickListener {
            val context = holder.itemView.context
            val builder = android.app.AlertDialog.Builder(context)
            builder.setTitle("Редагувати завдання")

            val input = android.widget.EditText(context)
            input.setText(task) // Підставляємо старий текст
            builder.setView(input)

            builder.setPositiveButton("Зберегти") { dialog, _ ->
                val updatedTask = input.text.toString()
                val currentPos = holder.adapterPosition
                if (updatedTask.isNotEmpty() && currentPos != RecyclerView.NO_POSITION) {
                    taskList[currentPos] = updatedTask
                    notifyItemChanged(currentPos)
                }
            }

            builder.setNegativeButton("Скасувати") { dialog, _ -> dialog.cancel() }
            builder.show()
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }
}