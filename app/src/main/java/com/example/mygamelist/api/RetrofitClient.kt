package com.example.mygamelist.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitClient {

    companion object{
        const val API_KEY = "cb052be79c0f4656810bf8d920596eff"
        const val GAMES_URL = "games?key=$API_KEY"
        const val BASE_URL = "https://api.rawg.io/api/"
    }
    val instance : RawgApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(RawgApi::class.java)
    }
}