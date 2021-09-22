package com.example.gb_my_app

import android.app.Application
import com.example.gb_my_app.model.RepositoryImpl

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // Создать репозиторий приложения.
        RepositoryImpl.create(this)
    }
}
