package com.example.gb_my_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gb_my_app.ui.view.MainFragment
import com.example.gb_my_app.ui.view.MovieFragment
import com.example.gb_my_app.utils.toVisibility
import com.google.android.material.progressindicator.LinearProgressIndicator

class MainActivity : AppCompatActivity(), MainFragment.Callbacks {

    private val progressIndicator: LinearProgressIndicator by lazy {
        findViewById(R.id.progress_indicator)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    /**
     * Обработать событие "клик" по элементу списка.
     *
     * @param movieID идентификатор фильма.
     */
    override fun onSelectMovie(movieID: Int) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, MovieFragment.newInstance(movieID))
            .addToBackStack(null)
            .commit()
    }

    override fun onShowProgress(visibilityMode: Int) {
        progressIndicator toVisibility visibilityMode
    }
}
