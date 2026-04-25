package com.example.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.ui.ExpenseAdapter
import com.example.project.ui.ExpenseViewModel

class HomeFragment : Fragment() {

    private val viewModel: ExpenseViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val etTitle = view.findViewById<EditText>(R.id.etTitle)
        val etAmount = view.findViewById<EditText>(R.id.etAmount)
        val btnAdd = view.findViewById<Button>(R.id.btnAdd)
        val tvRates = view.findViewById<TextView>(R.id.tvRates)
        val spinner = view.findViewById<Spinner>(R.id.spinnerCategory)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        val categories = arrayOf("Еда", "Транспорт", "Жилье", "Развлечения", "Другое")
        spinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, categories)

        val adapter = ExpenseAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewModel.allExpenses.asLiveData().observe(viewLifecycleOwner) { list ->
            adapter.updateData(list)
        }

        viewModel.exchangeRate.observe(viewLifecycleOwner) { rate ->
            tvRates.text = rate
        }

        btnAdd.setOnClickListener {
            val title = etTitle.text.toString()
            val amountStr = etAmount.text.toString()
            if (title.isNotEmpty() && amountStr.isNotEmpty()) {
                viewModel.addExpense(title, amountStr.toDouble(), spinner.selectedItem.toString())
                etTitle.text.clear()
                etAmount.text.clear()
                Toast.makeText(context, "Добавлено!", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.loadRates("BYN")
        return view
    }
}