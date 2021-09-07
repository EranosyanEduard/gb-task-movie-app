package com.example.gb_my_app.utils

class MyHelpers {

    companion object {
        fun convertISODateToHumanDate(isoDate: String) = isoDate
            .split("-")
            .reversed()
            .joinToString(".")
    }
}
