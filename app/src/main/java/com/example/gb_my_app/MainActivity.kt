package com.example.gb_my_app

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.gb_my_app.ui.view.*
import com.example.gb_my_app.utils.toVisibility
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.progressindicator.LinearProgressIndicator

interface SharedPreferencesCallbacks {
    fun editBooleanPreference(key: String, value: Boolean)
    fun getBooleanPreference(key: String, defaultValue: Boolean): Boolean
}

class MainActivity : AppCompatActivity(), SharedPreferencesCallbacks, MainFragment.Callbacks,
    ActorsFragment.Callbacks {

    private val progressIndicator: LinearProgressIndicator by lazy {
        findViewById(R.id.progress_indicator)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        addNavItemSelectedEventListener()

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Заполнить меню контентом, определенным в файле main_screen_menu
        menuInflater.inflate(R.menu.main_screen_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * Обработать выбор элемента меню на основной панели приложения.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        // Вернуть логическое значение: true - завершить обработку, false - продолжить
        // обработку, например, в "дочерних" фрагментах
        R.id.menu_item_account -> {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, CommentedMovieFragment.newInstance())
                .addToBackStack(null)
                .commitAllowingStateLoss()
            true
        }
        R.id.menu_item_app_settings -> {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, SettingsFragment.newInstance())
                .addToBackStack(null)
                .commitAllowingStateLoss()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    /**
     * Обработать выбор элемента навигационного меню в подвале активити.
     */
    private fun addNavItemSelectedEventListener() {
        val navMenu: BottomNavigationView = findViewById(R.id.bottom_nav_menu)

        navMenu.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.page_home -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, MainFragment.newInstance())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.page_actors -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, ActorsFragment.newInstance())
                        .commitAllowingStateLoss()
                    true
                }
                else -> false
            }
        }
    }

    /**
     * Редактировать shared preferences логического типа.
     *
     * @param key ключ, который позволит извлекать значение [value].
     * @param value значение, соответствующее ключу [key].
     */
    override fun editBooleanPreference(key: String, value: Boolean) {
        getPreferences(Context.MODE_PRIVATE).edit().apply {
            putBoolean(key, value)
            apply()
        }
    }

    override fun getBooleanPreference(key: String, defaultValue: Boolean): Boolean =
        this.getPreferences(Context.MODE_PRIVATE).getBoolean(key, defaultValue)

    /**
     * Обработать событие "клик" по элементу списка актеров.
     *
     * @param actorId идентификатор актера.
     */
    override fun onSelectActor(actorId: Int) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, ActorFragment.newInstance(actorId))
            .addToBackStack(null)
            .commit()
    }

    /**
     * Обработать событие "клик" по элементу списка фильмов.
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
