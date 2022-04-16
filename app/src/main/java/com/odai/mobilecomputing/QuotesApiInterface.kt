package com.odai.mobilecomputing

import retrofit2.Call
import retrofit2.http.GET

interface QuotesApiInterface {
    @GET("qod?language=en")
    fun getQuote(): Call<Quote>
}