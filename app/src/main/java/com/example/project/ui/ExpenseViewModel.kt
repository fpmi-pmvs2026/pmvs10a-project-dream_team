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

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ExpenseRepository
    val allExpenses: Flow<List<ExpenseEntity>>

    // Данные для UI (курсы и статистика)
    val exchangeRate = MutableLiveData<String>()
    val totalSum = MutableLiveData<Double>()
    val categorySum = MutableLiveData<Double>()

    init {
        val dao = AppDatabase.getDatabase(application).expenseDao()
        repository = ExpenseRepository(dao, RetrofitInstance.api)
        allExpenses = repository.allExpenses
    }

    // Добавление расхода
    fun addExpense(title: String, amount: Double, category: String) {
        viewModelScope.launch {
            repository.insert(ExpenseEntity(title = title, amount = amount, category = category))
        }
    }

    // Расчет статистики
    fun calculateStats(expenses: List<ExpenseEntity>, selectedCategory: String) {
        val total = expenses.sumOf { it.amount }
        totalSum.postValue(total)

        val catSum = expenses.filter { it.category == selectedCategory }.sumOf { it.amount }
        categorySum.postValue(catSum)
    }

    // Загрузка курса валют
    fun loadRates(base: String) {
        viewModelScope.launch {
            try {
                val response = repository.getLatestRates(base)
                val usd = response.rates["USD"]
                if (usd != null) {
                    exchangeRate.postValue("Курс USD: $usd")
                } else {
                    exchangeRate.postValue("Курс недоступен")
                }
            } catch (e: Exception) {
                exchangeRate.postValue("Ошибка сети")
            }
        }
    }
}