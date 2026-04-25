package com.example.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.ui.ExpenseViewModel
import com.example.project.ui.StatsAdapter
import java.util.Locale

class StatsFragment : Fragment() {

    private val viewModel: ExpenseViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_stats, container, false)
        val tvTotal = view.findViewById<TextView>(R.id.tvTotalStats)
        val rvStats = view.findViewById<RecyclerView>(R.id.rvStatsCategories)

        val adapter = StatsAdapter(emptyList())
        rvStats.layoutManager = LinearLayoutManager(context)
        rvStats.adapter = adapter

        viewModel.allExpenses.asLiveData().observe(viewLifecycleOwner) { list ->
            val total = list.sumOf { it.amount }
            tvTotal.text = String.format(Locale.US, "%,.2f BYN", total)

            // Группируем данные для карточек
            val categoryData = list.groupBy { it.category }
                .map { it.key to it.value.sumOf { exp -> exp.amount } }
                .sortedByDescending { it.second } // Самые крупные расходы вверху

            adapter.updateData(categoryData)
        }

        return view
    }
}