package com.example.gb_my_app.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.gb_my_app.R
import com.example.gb_my_app.model.Movie
import com.example.gb_my_app.repository.RemoteDataSource
import com.example.gb_my_app.utils.convertToHumanDate
import com.example.gb_my_app.utils.setTextToView
import com.squareup.picasso.Picasso

class MovieAdapter(
    private val movieList: List<Movie>,
    private val callbacks: MainFragment.Callbacks?,

    ) : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    inner class MovieHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(movie: Movie) {
            movie.apply {
                val mapOfTextViewIdAndText: Map<Int, String> = mapOf(
                    R.id.movie_title to title,
                    R.id.movie_title_original to originalTitle,
                    R.id.movie_vote_average to voteAverage.toString(),
                    R.id.movie_release_date to releaseDate.convertToHumanDate().let { "Релиз: $it" }
                )

                mapOfTextViewIdAndText.forEach { (resourceId, text) ->
                    itemView.setTextToView(resourceId, text)
                }

                itemView.setOnClickListener { callbacks?.onSelectMovie(id) }
            }
        }

        fun bindImage(lambda: (imageView: ImageView) -> Unit) {
            lambda(itemView.findViewById(R.id.movie_image))
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
        val fullImageUrl = "${RemoteDataSource.UrlEnum.Image.url}/${currentMovie.posterPath}"

        holder.bind(currentMovie)
        holder.bindImage { imageView -> Picasso.get().load(fullImageUrl).into(imageView) }
    }
}
