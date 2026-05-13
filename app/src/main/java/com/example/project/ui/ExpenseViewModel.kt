package com.example.project.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.project.data.AppDatabase
import com.example.project.data.ExpenseEntity
import com.example.project.data.ExpenseRepository
import com.example.project.network.RetrofitInstance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.Locale

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ExpenseRepository
    val allExpenses: Flow<List<ExpenseEntity>>
    val exchangeRate = MutableLiveData<String>()
    var rateUSDToBYN: Double = 3.25

    init {
        val dao = AppDatabase.getDatabase(application).expenseDao()
        repository = ExpenseRepository(dao, RetrofitInstance.api)
        allExpenses = repository.allExpenses
    }

    fun addExpense(title: String, amount: Double, category: String, currency: String) {
        viewModelScope.launch {
            val newExpense = ExpenseEntity(
                title = title,
                amount = amount,
                category = category,
                currency = currency
            )
            repository.insert(newExpense)
        }
    }

    fun loadRates(base: String) {
        viewModelScope.launch {
            try {
                val response = repository.getLatestRates(base)
                val rate = response.rates["USD"] ?: 1.0
                rateUSDToBYN = 1.0 / rate
                exchangeRate.postValue("1 USD = ${String.format(Locale.US, "%.2f", rateUSDToBYN)} BYN")
            } catch (e: Exception) {
                rateUSDToBYN = 3.25
                exchangeRate.postValue("Курс: 3.25 BYN (оффлайн)")
                e.printStackTrace()
            }
        }
    }
}