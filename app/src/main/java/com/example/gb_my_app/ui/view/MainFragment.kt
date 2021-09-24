package com.example.gb_my_app.ui.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gb_my_app.AppState
import com.example.gb_my_app.databinding.MainFragmentBinding
import com.example.gb_my_app.utils.showSnackbar
import com.example.gb_my_app.utils.toVisibility
import com.example.gb_my_app.view_model.MainViewModel

const val ALLOW_ADULT_CONTENT = "ALLOW_ADULT_CONTENT"

class MainFragment : Fragment() {

    /**
     * Использовать интерфейс для организации взаимодействия между интерфейсом
     * хост-активити и фрагмента.
     */
    interface Callbacks {
        fun onSelectMovie(movieID: Int)
        fun onShowProgress(visibilityMode: Int)
    }

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private var callbacks: Callbacks? = null

    // View binding
    private var viewBindingRef: MainFragmentBinding? = null

    private val viewBinding get() = viewBindingRef!!

    private var allowAdultContent = true

    override fun onAttach(context: Context) {
        super.onAttach(context)

        /**
         * Переопределить свойство callbacks. В данном случае context-ом является
         * активити, что позволяет нам получить доступ к ее реализации интерфейса
         * Callbacks.
         */
        callbacks = context as Callbacks
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBindingRef = MainFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Воспользоваться публичными интерфейсами, которые предоставляет ViewModel
         * для извлечения данных и контроля за состоянием таких данных.
         */
        viewModel.movieListLiveData.observe(viewLifecycleOwner) { appState -> render(appState) }
        handleSharedPreferences()
        viewModel.getMovieListNowPlaying()
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBindingRef = null
    }

    private fun handleSharedPreferences() {
        activity?.also {
            allowAdultContent = it.getPreferences(Context.MODE_PRIVATE)
                .getBoolean(ALLOW_ADULT_CONTENT, true)
        }
    }

    /**
     * Выполнить рендер фрагмента, руководствуясь состоянием приложения [appState].
     *
     * @param appState актуальное состояние приложения.
     */
    private fun render(appState: AppState) {
        when (appState) {
            is AppState.Failure -> viewBinding.also { vb ->
                callbacks?.onShowProgress(View.INVISIBLE)

                vb.main.showSnackbar("Ошибка") {
                    viewModel.getMovieListNowPlaying()
                }
            }
            is AppState.Loading -> callbacks?.onShowProgress(View.VISIBLE)
            is AppState.Success -> viewBinding.also { vb ->
                val movieList = (appState as AppState.MovieListFetched).movieList

                Log.i("foobarbaz",allowAdultContent.toString())
                // Отфильтровать массив фильмов, руководствуясь значением allowAdultContent
                val filteredMovieList = if (allowAdultContent) {
                    movieList
                } else {
                    movieList.filter { !it.adult }
                }

                vb.movieRecyclerView.adapter = MovieAdapter(filteredMovieList, callbacks)

                callbacks?.onShowProgress(View.INVISIBLE)
                vb.main toVisibility View.VISIBLE
            }
        }
    }
}
