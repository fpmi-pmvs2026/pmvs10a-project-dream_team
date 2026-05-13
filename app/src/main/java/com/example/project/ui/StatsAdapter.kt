package com.example.project.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project.R

class StatsAdapter(private var categories: List<Pair<String, Double>>) :
    RecyclerView.Adapter<StatsAdapter.ViewHolder>() {

    private var currentCurrency: String = "BYN"

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tvStatCategoryName)
        val amount: TextView = view.findViewById(R.id.tvStatCategorySum)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stat, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (categoryName, categoryAmount) = categories[position]
        holder.name.text = categoryName
        holder.amount.text = String.format(java.util.Locale.US, "%,.2f %s", categoryAmount, currentCurrency)
    }

    override fun getItemCount() = categories.size

    fun updateData(newCategories: List<Pair<String, Double>>, currency: String) {
        this.categories = newCategories
        this.currentCurrency = currency
        notifyDataSetChanged()
    }
}