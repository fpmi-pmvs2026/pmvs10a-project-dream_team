package com.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.example.project.ui.ExpenseViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: ExpenseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.addExpense("Покупка через ViewModel", 25.0, "Еда")
        viewModel.loadRates("BYN")

    }
}