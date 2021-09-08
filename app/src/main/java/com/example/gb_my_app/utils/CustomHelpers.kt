package com.example.gb_my_app.utils

import android.view.View

fun String.convertToHumanDate() = split("-").reversed().joinToString(".")

infix fun View.toVisibility(visibilityMode: Int) {
    visibility = visibilityMode
}