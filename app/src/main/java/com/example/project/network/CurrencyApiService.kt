package com.example.project.network

import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyApiService {

    @GET("https://open.er-api.com/v6/latest/{base}")
    suspend fun getRates(@Path("base") baseCurrency: String): CurrencyResponse
}