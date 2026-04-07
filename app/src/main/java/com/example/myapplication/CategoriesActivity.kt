package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CategoriesActivity : AppCompatActivity() {

    // Списки наших даних (Назва категорії та її опис)
    private val categories = listOf("🏠 Дім", "💻 Робота", "📚 Навчання", "🏃‍♂️ Здоров'я та Спорт", "🛒 Покупки")
    private val descriptions = listOf(
        "Завдання, пов'язані з прибиранням, ремонтом та хатніми справами.",
        "Робочі зустрічі, дедлайни, звіти та дзвінки клієнтам.",
        "Домашні завдання, лекції, курси та саморозвиток.",
        "Тренування, візити до лікаря, прийом вітамінів та дієта.",
        "Списки продуктів, подарунки на свята та побутова хімія."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        val rvCategories = findViewById<RecyclerView>(R.id.rvCategories)
        rvCategories.layoutManager = LinearLayoutManager(this)

        // Підключаємо наш Адаптер
        rvCategories.adapter = CategoryAdapter(categories, descriptions)
    }

    // --- ВНУТРІШНІЙ АДАПТЕР ---
    class CategoryAdapter(private val catList: List<String>, private val descList: List<String>) :
        RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

        class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            // Використовуємо стандартний системний TextView
            val tvName: TextView = itemView.findViewById(android.R.id.text1)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
            // Вбудований у Android простий дизайн рядка (simple_list_item_1)
            val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
            return CategoryViewHolder(view)
        }

        override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
            holder.tvName.text = catList[position]
            holder.tvName.textSize = 18f
            holder.tvName.setPadding(32, 32, 32, 32)

            // Логіка Pop-up вікна при натисканні
            holder.itemView.setOnClickListener {
                val context = holder.itemView.context
                val builder = android.app.AlertDialog.Builder(context)
                builder.setTitle(catList[position])
                builder.setMessage(descList[position])
                builder.setPositiveButton("Зрозуміло") { dialog, _ -> dialog.dismiss() }
                builder.show()
            }
        }

        override fun getItemCount() = catList.size
    }
}