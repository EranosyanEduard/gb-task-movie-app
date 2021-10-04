package com.example.gb_my_app.api_client

import com.example.gb_my_app.model.Actor
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ActorApi {

    @GET("popular")
    fun fetchActorListPopular(
        @Query("api_key")
        apiKey: String,

        @Query("language")
        lang: String,

        @Query("page")
        page: Int

    ): Call<ActorApiResponse.ActorListPopular>

    @GET("{actorID}")
    fun fetchActorById(
        @Path("actorID")
        movieID: Int,

        @Query("api_key")
        apiKey: String,

        @Query("language")
        lang: String

    ): Call<Actor>
}
