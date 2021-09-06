package com.example.gb_my_app.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gb_my_app.databinding.MainFragmentBinding
import com.example.gb_my_app.view_model.MainViewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    // View binding
    private var fragmentBindingRef: MainFragmentBinding? = null

    private val fragmentBinding get() = fragmentBindingRef!!

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
            fragmentBinding.movieRecyclerView.adapter = MovieAdapter(it)
        })
    }

    override fun onDestroy() {
        super.onDestroy()

        fragmentBindingRef = null
    }
}