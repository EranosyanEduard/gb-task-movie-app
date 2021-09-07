package com.example.gb_my_app.ui.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gb_my_app.databinding.MainFragmentBinding
import com.example.gb_my_app.viewmodel.MainViewModel

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

    private lateinit var viewModel: MainViewModel

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

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.movieListLiveData.observe(viewLifecycleOwner, {
            fragmentBinding.movieRecyclerView.adapter = MovieAdapter(it, callbacks)
        })
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentBindingRef = null
    }
}