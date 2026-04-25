package com.example.project.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project.R
import java.util.Locale

class StatsAdapter(private var items: List<Pair<String, Double>>) : RecyclerView.Adapter<StatsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tvStatCategoryName)
        val sum: TextView = view.findViewById(R.id.tvStatCategorySum)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_stats_category, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (category, amount) = items[position]
        holder.name.text = category
        holder.sum.text = String.format(Locale.US, "%,.2f BYN", amount)
    }

    override fun getItemCount() = items.size

    fun updateData(newItems: List<Pair<String, Double>>) {
        items = newItems
        notifyDataSetChanged()
    }
}