package com.example.gb_my_app.api_client

import com.example.gb_my_app.model.Actor
import com.google.gson.annotations.SerializedName

sealed class ActorApiResponse {

    class ActorListPopular : ActorApiResponse() {
        @SerializedName("results")
        lateinit var actorList: List<Actor>
    }
}
