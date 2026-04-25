package com.example.project.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project.R
import com.example.project.data.ExpenseEntity

class ExpenseAdapter(private var expenses: List<ExpenseEntity>) : RecyclerView.Adapter<ExpenseAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvItemTitle)
        val category: TextView = view.findViewById(R.id.tvItemCategory)
        val amount: TextView = view.findViewById(R.id.tvItemAmount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_expense, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = expenses[position]
        holder.title.text = item.title
        holder.category.text = item.category
        holder.amount.text = "${item.amount} BYN"
    }

    override fun getItemCount() = expenses.size

    fun updateData(newExpenses: List<ExpenseEntity>) {
        expenses = newExpenses
        notifyDataSetChanged()
    }
}