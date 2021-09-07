package com.example.gb_my_app.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gb_my_app.databinding.MovieFragmentBinding
import com.example.gb_my_app.model.Movie
import com.example.gb_my_app.utils.MyHelpers

private const val ARG_MOVIE_ID = "MOVIE_ID"

class MovieFragment : Fragment() {

    companion object {
        fun newInstance(movieID: Int) = MovieFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_MOVIE_ID, movieID)
            }
        }
    }

    private lateinit var viewModel: MovieViewModel

    private var paramMovieID: Int? = null

    // View binding.
    private var fragmentBindingRef: MovieFragmentBinding? = null

    private val fragmentBinding get() = fragmentBindingRef!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        arguments?.let {
            paramMovieID = it.getInt(ARG_MOVIE_ID)

            viewModel.getMovieById(paramMovieID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        fragmentBindingRef = MovieFragmentBinding.inflate(inflater, container, false)
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.movieLiveData.observe(viewLifecycleOwner, { updateUI(it) })
    }

    private fun updateUI(movie: Movie) {
        fragmentBinding.apply {
            movieTitle.text = movie.title
            movieTitleOriginal.text = movie.originalTitle
            movieVoteAverage.text = "${movie.voteAverage}"
            movieOverview.text = movie.overview

            movieReleaseDate.text = MyHelpers
                .convertISODateToHumanDate(movie.releaseDate)
                .let { "Релиз: $it" }
        }

    }
}