package com.example.project.network

data class CurrencyResponse(
    val base_code: String,
    val rates: Map<String, Double>
)