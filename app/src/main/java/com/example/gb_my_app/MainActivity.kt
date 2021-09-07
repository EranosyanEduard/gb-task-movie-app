package com.example.gb_my_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gb_my_app.ui.view.MainFragment
import com.example.gb_my_app.ui.view.MovieFragment

class MainActivity : AppCompatActivity(), MainFragment.Callbacks {

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
     * Handle click on movie list item.
     *
     * @param movieID movie identifier.
     */
    override fun onMovieSelected(movieID: Int) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, MovieFragment.newInstance(movieID))
            .addToBackStack(null)
            .commit()
    }
}