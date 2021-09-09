package com.example.gb_my_app.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gb_my_app.R
import com.example.gb_my_app.model.Movie
import com.example.gb_my_app.utils.convertToHumanDate

class MovieAdapter(
    private val movieList: List<Movie>,
    private val callbacks: MainFragment.Callbacks?,

    ) : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    inner class MovieHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(movie: Movie) {
            itemView.also { iv ->
                movie.apply {
                    iv.findViewById<TextView>(R.id.movie_title).text = title
                    iv.findViewById<TextView>(R.id.movie_title_original).text = originalTitle
                    iv.findViewById<TextView>(R.id.movie_vote_average).text = "$voteAverage"

                    iv.findViewById<TextView>(R.id.movie_release_date).text = releaseDate
                        .convertToHumanDate()
                        .let { "Релиз: $it" }

                    iv.setOnClickListener { callbacks?.onMovieSelected(id) }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.main_fragment_list_item, parent, false)

        return MovieHolder(view)
    }

    override fun getItemCount() = movieList.size

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val currentMovie: Movie = movieList[position]

        holder.bind(currentMovie)
    }
}