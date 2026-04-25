package com.example.project.data

import com.example.project.network.CurrencyApiService
import com.example.project.network.CurrencyResponse
import kotlinx.coroutines.flow.Flow

class ExpenseRepository(
    private val expenseDao: ExpenseDao,
    private val apiService: CurrencyApiService
) {
    val allExpenses: Flow<List<ExpenseEntity>> = expenseDao.getAllExpenses()

    suspend fun insert(expense: ExpenseEntity) {
        expenseDao.insertExpense(expense)
    }

    suspend fun getLatestRates(base: String): CurrencyResponse {
        return apiService.getRates(base)
    }
}