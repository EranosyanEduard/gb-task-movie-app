package com.example.gb_my_app.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.gb_my_app.R
import com.example.gb_my_app.model.Movie
import com.example.gb_my_app.utils.convertToHumanDate
import com.example.gb_my_app.utils.setTextToView
import com.squareup.picasso.Picasso

class CommentedMovieAdapter(private val movieList: List<Movie>) :
    RecyclerView.Adapter<CommentedMovieAdapter.CommentedMovieHolder>() {

    inner class CommentedMovieHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(movie: Movie) {
            movie.apply {
                val mapOfTextViewIdAndText: Map<Int, String> = mapOf(
                    R.id.movie_title to title,
                    R.id.movie_title_original to originalTitle,
                    R.id.movie_vote_average to voteAverage.toString(),
                    R.id.movie_overview to overview,
                    R.id.movie_user_comment to userComment,
                    R.id.movie_release_date to releaseDate.convertToHumanDate().let { "Релиз: $it" }
                )

                mapOfTextViewIdAndText.forEach { (resourceId, text) ->
                    itemView.setTextToView(resourceId, text)
                }
            }
        }

        fun bindImage(cb: (imageView: ImageView) -> Unit) =
            cb(itemView.findViewById(R.id.movie_image))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentedMovieHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.commented_movie_fragment_list_item, parent, false)

        return CommentedMovieHolder(view)
    }

    override fun onBindViewHolder(holder: CommentedMovieHolder, position: Int) {
        val currentMovie: Movie = movieList[position]

        holder.bind(currentMovie)
        holder.bindImage { imageView: ImageView ->
            Picasso.get().load(currentMovie.posterPath).into(imageView)
        }
    }

    override fun getItemCount() = movieList.size
}
