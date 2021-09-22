package com.example.gb_my_app.ui.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gb_my_app.AppState
import com.example.gb_my_app.databinding.MovieFragmentBinding
import com.example.gb_my_app.repository.RemoteDataSource
import com.example.gb_my_app.utils.convertToHumanDate
import com.example.gb_my_app.utils.showSnackbar
import com.example.gb_my_app.utils.toVisibility
import com.example.gb_my_app.view_model.MovieViewModel
import com.squareup.picasso.Picasso

private const val ARG_MOVIE_ID = "MOVIE_ID"

class MovieFragment : Fragment() {

    companion object {
        fun newInstance(movieID: Int) = MovieFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_MOVIE_ID, movieID)
            }
        }
    }

    private val viewModel: MovieViewModel by lazy {
        ViewModelProvider(this).get(MovieViewModel::class.java)
    }

    private var paramMovieID: Int? = null

    private var callbacks: MainFragment.Callbacks? = null

    // View binding.
    private var viewBindingRef: MovieFragmentBinding? = null

    private val viewBinding get() = viewBindingRef!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.also {
            paramMovieID = it.getInt(ARG_MOVIE_ID)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        /**
         * Переопределить свойство callbacks. В данном случае context-ом является
         * активити, что позволяет нам получить доступ к ее реализации интерфейса
         * Callbacks.
         */
        callbacks = context as MainFragment.Callbacks
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBindingRef = MovieFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Воспользоваться публичными интерфейсами, которые предоставляет ViewModel
         * для извлечения данных и контроля за состоянием таких данных.
         */
        viewModel.movieLiveData.observe(viewLifecycleOwner, { appState -> render(appState) })
        viewModel.getMovieById(paramMovieID)
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBindingRef = null
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

                vb.movieContainer.showSnackbar("Ошибка") {
                    viewModel.getMovieById(paramMovieID)
                }
            }
            is AppState.Loading -> callbacks?.onShowProgress(View.VISIBLE)
            is AppState.Success -> viewBinding.also { vb ->
                val movie = (appState as AppState.MovieFetched).movie
                val fullImageUrl = "${RemoteDataSource.UrlEnum.Image.url}/${movie.posterPath}"

                movie.apply {
                    vb.movieTitle.text = title
                    vb.movieTitleOriginal.text = originalTitle
                    vb.movieVoteAverage.text = "$voteAverage"
                    vb.movieOverview.text = overview
                    vb.movieReleaseDate.text = releaseDate.convertToHumanDate().let { "Релиз: $it" }
                }

                callbacks?.onShowProgress(View.INVISIBLE)
                vb.movieContainer toVisibility View.VISIBLE

                Picasso.get().load(fullImageUrl).into(vb.movieImage)
            }
        }
    }
}
