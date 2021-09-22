package com.example.gb_my_app.utils

import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

fun View.setTextToView(resourceId: Int, text: String) {
    findViewById<TextView>(resourceId).text = text
}

fun View.showSnackbar(text: String, actionCb: () -> Unit) {
    Snackbar
        .make(this, text, Snackbar.LENGTH_INDEFINITE)
        .setAction("Перезагрузить") { actionCb() }
        .show()
}

fun String.convertToHumanDate() = split("-").reversed().joinToString(".")

infix fun View.toVisibility(visibilityMode: Int) {
    visibility = visibilityMode
}