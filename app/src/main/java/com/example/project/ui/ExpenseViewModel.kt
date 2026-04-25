package com.example.project.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.data.AppDatabase
import com.example.project.data.ExpenseEntity
import com.example.project.data.ExpenseRepository
import com.example.project.network.RetrofitInstance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ExpenseRepository
    val allExpenses: Flow<List<ExpenseEntity>>

    init {
        val dao = AppDatabase.getDatabase(application).expenseDao()
        repository = ExpenseRepository(dao, RetrofitInstance.api)
        allExpenses = repository.allExpenses
    }

    fun addExpense(title: String, amount: Double, category: String) {
        viewModelScope.launch {
            val newExpense = ExpenseEntity(title = title, amount = amount, category = category)
            repository.insert(newExpense)
        }
    }

    fun loadRates(base: String) {
        viewModelScope.launch {
            try {
                val response = repository.getLatestRates(base)
            } catch (e: Exception) {
            }
        }
    }
}