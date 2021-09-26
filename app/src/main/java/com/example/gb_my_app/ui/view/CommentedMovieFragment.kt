package com.example.gb_my_app.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gb_my_app.databinding.CommentedMovieFragmentBinding

class CommentedMovieFragment : Fragment() {

    companion object {
        fun newInstance() = CommentedMovieFragment()
    }

    private val viewModel: CommentedMovieViewModel by lazy {
        ViewModelProvider(this).get(CommentedMovieViewModel::class.java)
    }

    // View binding
    private var viewBindingRef: CommentedMovieFragmentBinding? = null

    private val viewBinding get() = viewBindingRef!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBindingRef = CommentedMovieFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.movieListLiveData.observe(viewLifecycleOwner, { movieList ->
            viewBinding.commentedMovieRecyclerView.adapter = CommentedMovieAdapter(movieList)
        })
    }
}
