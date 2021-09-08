package com.example.gb_my_app.ui.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gb_my_app.AppState
import com.example.gb_my_app.databinding.MainFragmentBinding
import com.example.gb_my_app.utils.toVisibility
import com.example.gb_my_app.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    /**
     * Use interface to pass "event" click from fragment to host-activity.
     */
    interface Callbacks {
        fun onMovieSelected(movieID: Int)
    }

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private var callbacks: Callbacks? = null

    // View binding.
    private var fragmentBindingRef: MainFragmentBinding? = null

    private val fragmentBinding get() = fragmentBindingRef!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Here context is activity.
        callbacks = context as Callbacks
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        fragmentBindingRef = MainFragmentBinding.inflate(inflater, container, false)
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel
            .getMovieListLiveData()
            .observe(viewLifecycleOwner, { renderUI(it) })

        viewModel.getMovieList(viewLifecycleOwner)
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentBindingRef = null
    }

    private fun renderUI(appState: AppState) {
        when (appState) {
            is AppState.Failure -> fragmentBinding.apply {
                progressIndicator toVisibility View.VISIBLE

                Snackbar
                    .make(mainWrapper, "Ошибка", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Перезагрузить") {
                        viewModel.getMovieList(viewLifecycleOwner)
                    }
                    .show()
            }
            is AppState.Loading -> {
                fragmentBinding.progressIndicator toVisibility View.VISIBLE
            }
            is AppState.Success -> fragmentBinding.apply {
                val movieList = (appState as AppState.MovieListFetched).movieList

                movieRecyclerView.adapter = MovieAdapter(movieList, callbacks)

                progressIndicator toVisibility View.INVISIBLE
                mainWrapper toVisibility View.VISIBLE
            }
        }
    }
}