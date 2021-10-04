package com.example.gb_my_app.ui.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gb_my_app.AppState
import com.example.gb_my_app.databinding.ActorsFragmentBinding
import com.example.gb_my_app.model.Actor
import com.example.gb_my_app.utils.showSnackbar
import com.example.gb_my_app.utils.toVisibility
import com.example.gb_my_app.view_model.ActorsViewModel

class ActorsFragment : Fragment() {

    interface Callbacks {
        fun onSelectActor(actorId: Int)
    }

    companion object {
        fun newInstance() = ActorsFragment()
    }

    private val viewModel: ActorsViewModel by lazy {
        ViewModelProvider(this).get(ActorsViewModel::class.java)
    }

    // Callbacks
    private var actorsCallbacks: Callbacks? = null
    private var callbacks: MainFragment.Callbacks? = null

    // View binding
    private var viewBindingRef: ActorsFragmentBinding? = null

    private val viewBinding get() = viewBindingRef!!

    override fun onAttach(context: Context) {
        super.onAttach(context)

        actorsCallbacks = context as Callbacks
        callbacks = context as MainFragment.Callbacks
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBindingRef = ActorsFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.actorListLiveData.observe(viewLifecycleOwner, { appState -> render(appState) })
        viewModel.getActorListPopular()
    }

    override fun onDetach() {
        super.onDetach()

        actorsCallbacks = null
        callbacks = null
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBindingRef = null
    }

    private fun render(appState: AppState) {
        when (appState) {
            is AppState.Failure -> viewBinding.also { vb ->
                vb.main.showSnackbar("Ошибка") {
                    viewModel.getActorListPopular()
                }
            }
            is AppState.Loading -> callbacks?.onShowProgress(View.VISIBLE)
            is AppState.Success -> viewBinding.also { vb ->
                val actorList: List<Actor> = (appState as AppState.ActorListFetched).actorList

                vb.actorRecyclerView.adapter = ActorAdapter(actorList, actorsCallbacks)

                callbacks?.onShowProgress(View.INVISIBLE)
                vb.main toVisibility View.VISIBLE
            }
        }
    }
}
