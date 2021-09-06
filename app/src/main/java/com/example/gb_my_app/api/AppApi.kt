package com.example.gb_my_app.api

import retrofit2.Call
import retrofit2.http.GET

interface AppApi {

    @GET("now_playing"
            + "?api_key=c7dbc5f1d33bf032496be926b19514e6"
            + "&language=ru-RU"
            + "&region=RU")
    fun fetchMovieListNowPlaying(): Call<AppApiResponse>
}