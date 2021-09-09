package com.example.gb_my_app.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun Snackbar.showReloadAction(actionCb: () -> Unit) {
    setAction("Перезагрузить") { actionCb() }.show()
}

fun String.convertToHumanDate() = split("-").reversed().joinToString(".")

infix fun View.toVisibility(visibilityMode: Int) {
    visibility = visibilityMode
}