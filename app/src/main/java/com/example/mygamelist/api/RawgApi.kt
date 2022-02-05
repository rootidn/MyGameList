package com.example.mygamelist.api

import com.example.mygamelist.response.PostResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RawgApi {

    @GET("${RetrofitClient.GAMES_URL}&dates=2022-01-01,2022-02-04")
    fun getNewestGames() : Call<PostResponse>

    @GET("${RetrofitClient.GAMES_URL}&ordering=~rating")
    fun getTopGames() : Call<PostResponse>

    @GET(RetrofitClient.GAMES_URL)
    fun getSearchGames(@Query("search") search : String) : Call<PostResponse>
}