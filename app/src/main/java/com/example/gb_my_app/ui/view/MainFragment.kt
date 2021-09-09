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
import com.example.gb_my_app.utils.showReloadAction
import com.example.gb_my_app.utils.toVisibility
import com.example.gb_my_app.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    /**
     * Use interface to pass "event" click from fragment to host-activity.
     */
    interface Callbacks {
        fun onMovieSelected(movieID: Int)

        fun onShowProgress(visibilityMode: Int)
    }

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private var callbacks: Callbacks? = null

    // View binding.
    private var viewBindingRef: MainFragmentBinding? = null

    private val viewBinding get() = viewBindingRef!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Here context is activity. Get access to activity functions.
        callbacks = context as Callbacks
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBindingRef = MainFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
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
        viewBindingRef = null
    }

    private fun renderUI(appState: AppState) {
        when (appState) {
            is AppState.Failure -> viewBinding.also { vb ->
                callbacks?.onShowProgress(View.INVISIBLE)

                Snackbar
                    .make(vb.main, "Ошибка", Snackbar.LENGTH_INDEFINITE)
                    .showReloadAction { viewModel.getMovieList(viewLifecycleOwner) }
            }
            is AppState.Loading -> callbacks?.onShowProgress(View.VISIBLE)
            is AppState.Success -> viewBinding.also { vb ->
                val movieList = (appState as AppState.MovieListFetched).movieList

                vb.movieRecyclerView.adapter = MovieAdapter(movieList, callbacks)

                callbacks?.onShowProgress(View.INVISIBLE)
                vb.main toVisibility View.VISIBLE
            }
        }
    }
}