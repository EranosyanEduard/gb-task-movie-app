package com.example.gb_my_app

import android.app.Application
import com.example.gb_my_app.model.Repository

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // Create app repository.
        Repository.create(this)
    }
}