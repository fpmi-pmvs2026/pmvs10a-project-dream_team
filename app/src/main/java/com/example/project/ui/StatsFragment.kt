package com.example.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.data.ExpenseEntity
import com.example.project.ui.ExpenseViewModel
import com.example.project.ui.StatsAdapter
import com.google.android.material.button.MaterialButtonToggleGroup
import java.util.Locale

class StatsFragment : Fragment() {

    private val viewModel: ExpenseViewModel by viewModels()
    private lateinit var adapter: StatsAdapter
    private var lastList: List<ExpenseEntity> = emptyList() // Храним список здесь

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_stats, container, false)

        val tvTotal = view.findViewById<TextView>(R.id.tvTotalStats)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvStatsCategories)
        val toggleGroup = view.findViewById<MaterialButtonToggleGroup>(R.id.toggleCurrencyGroup)

        adapter = StatsAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Наблюдаем за данными один раз
        viewModel.allExpenses.asLiveData().observe(viewLifecycleOwner) { list ->
            if (list != null) {
                lastList = list
                updateStats(tvTotal, toggleGroup.checkedButtonId == R.id.btnShowUsd)
            }
        }

        // Слушаем переключатель
        toggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                updateStats(tvTotal, checkedId == R.id.btnShowUsd)
            }
        }

        viewModel.loadRates("BYN")
        return view
    }

    private fun updateStats(tvTotal: TextView, showInUsd: Boolean) {
        val rate = if (viewModel.rateUSDToBYN > 0) viewModel.rateUSDToBYN else 3.25
        val currencyLabel = if (showInUsd) "USD" else "BYN"

        val processedList = lastList.map { expense ->
            val convertedAmount = when {
                showInUsd && expense.currency == "BYN" -> expense.amount / rate
                !showInUsd && expense.currency == "USD" -> expense.amount * rate
                else -> expense.amount
            }
            expense.copy(amount = convertedAmount)
        }

        val totalSum = processedList.sumOf { it.amount }
        tvTotal.text = String.format(Locale.US, "%,.2f %s", totalSum, currencyLabel)

        val categoryData = processedList.map {
            Category.getLocalizedName(requireContext(), it.category) to it.amount
        }
            .groupBy { it.first }
            .map { it.key to it.value.sumOf { item -> item.second } }
            .sortedByDescending { it.second }

        adapter.updateData(categoryData, currencyLabel)
    }
}