package com.example.gb_my_app.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gb_my_app.AppState
import com.example.gb_my_app.api_client.ActorApiResponse
import com.example.gb_my_app.model.Actor
import com.example.gb_my_app.model.RepositoryImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActorsViewModel : ViewModel() {

    /**
     * Лайв-дата - публичный интерфейс для контроля за состоянием данных.
     */
    val actorListLiveData: MutableLiveData<AppState> = MutableLiveData()

    private val repository = RepositoryImpl.getInstance()

    private val cbForGetActorListPopular = object : Callback<ActorApiResponse.ActorListPopular> {
        /**
         * Обработать успешный результат запроса.
         */
        override fun onResponse(
            call: Call<ActorApiResponse.ActorListPopular>,
            response: Response<ActorApiResponse.ActorListPopular>
        ) {
            val fetchedResponseBody: List<Actor> = response.body()?.actorList ?: emptyList()

            actorListLiveData.postValue(AppState.ActorListFetched(fetchedResponseBody))
        }

        /**
         * Обработать неудачный результат запроса.
         */
        override fun onFailure(call: Call<ActorApiResponse.ActorListPopular>, t: Throwable) {
            actorListLiveData.postValue(AppState.Failure(t))
        }
    }

    /**
     * Извлечь список актеров из удаленного источника (публичный интерфейс)
     */
    fun getActorListPopular() = getActorListPopularFromRepository()

    private fun getActorListPopularFromRepository() {
        actorListLiveData.value = AppState.Loading
        repository.getActorListPopular(cbForGetActorListPopular)
    }
}
