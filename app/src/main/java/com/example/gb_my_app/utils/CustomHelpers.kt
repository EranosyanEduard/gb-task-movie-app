package com.example.gb_my_app.utils

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.gb_my_app.model.Movie
import com.example.gb_my_app.model.db.MovieEntity
import com.google.android.material.snackbar.Snackbar

fun String.convertToHumanDate() = split("-").reversed().joinToString(".")

fun View.setTextToView(resourceId: Int, text: String) {
    findViewById<TextView>(resourceId).text = text
}

fun View.showSnackbar(text: String, actionCb: () -> Unit) {
    Snackbar
        .make(this, text, Snackbar.LENGTH_INDEFINITE)
        .setAction("Перезагрузить") { actionCb() }
        .show()
}

fun Movie.toEntity(comment: String, posterPath: String) = MovieEntity(
    adult = adult,
    movieId = id,
    originalTitle = originalTitle,
    overview = overview,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    userComment = comment,
    voteAverage = voteAverage
)

fun MovieEntity.toMovie() = Movie(
    id = movieId,
    adult = adult,
    originalTitle = originalTitle,
    overview = overview,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    userComment = userComment,
    voteAverage = voteAverage
)

infix fun Button.toIsEnabled(text: String) {
    isEnabled = text.isNotBlank()
}

infix fun View.toVisibility(visibilityMode: Int) {
    visibility = visibilityMode
}
