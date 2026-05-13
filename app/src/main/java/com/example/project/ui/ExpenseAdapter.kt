package com.example.project.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project.Category
import com.example.project.R
import com.example.project.data.ExpenseEntity
import java.util.Locale

class ExpenseAdapter(private var expenses: List<ExpenseEntity>) :
    RecyclerView.Adapter<ExpenseAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvItemTitle)
        val amount: TextView = view.findViewById(R.id.tvItemAmount)
        val category: TextView = view.findViewById(R.id.tvItemCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_expense, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val expense = expenses[position]
        holder.title.text = expense.title
        holder.amount.text = String.format(Locale.US, "%,.2f %s", expense.amount, expense.currency)
        holder.category.text = Category.getLocalizedName(holder.itemView.context, expense.category)
    }

    override fun getItemCount() = expenses.size

    fun updateData(newExpenses: List<ExpenseEntity>) {
        expenses = newExpenses
        notifyDataSetChanged()
    }
}