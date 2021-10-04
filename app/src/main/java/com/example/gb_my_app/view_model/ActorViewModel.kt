package com.example.gb_my_app.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gb_my_app.AppState
import com.example.gb_my_app.model.Actor
import com.example.gb_my_app.model.RepositoryImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val EXCEPTION_INVALID_ACTOR_ID_TEXT = "Недопустимый идентификатор актера"

class ActorViewModel : ViewModel() {
    /**
     * Лайв-дата - публичный интерфейс для контроля за состоянием данных.
     */
    val actorLiveData: MutableLiveData<AppState> = MutableLiveData()

    private val repository = RepositoryImpl.getInstance()

    private val cbForGetActorById = object : Callback<Actor> {
        /**
         * Обработать успешный результат запроса.
         */
        override fun onResponse(call: Call<Actor>, response: Response<Actor>) {
            val fetchedResponseBody: Actor = response.body() ?: Actor()

            actorLiveData.postValue(AppState.ActorFetched(fetchedResponseBody))
        }

        /**
         * Обработать неудачный результат запроса.
         */
        override fun onFailure(call: Call<Actor>, t: Throwable) {
            actorLiveData.postValue(AppState.Failure(t))
        }
    }

    /**
     * Извлечь подробную информацию об актере по его идентификатору (публичный интерфейс).
     *
     * @param actorID идентификатор актера.
     */
    fun getActorById(actorID: Int?) = getActorByIdFromRepository(actorID)

    private fun getActorByIdFromRepository(actorID: Int?) {
        actorLiveData.value = AppState.Loading

        if (actorID == null) {
            actorLiveData.value = AppState.Failure(Throwable(EXCEPTION_INVALID_ACTOR_ID_TEXT))
            return
        }

        repository.getActorById(actorID, cbForGetActorById)
    }
}
