package com.example.gb_my_app.ui.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gb_my_app.AppState
import com.example.gb_my_app.databinding.ActorFragmentBinding
import com.example.gb_my_app.model.Actor
import com.example.gb_my_app.utils.convertToHumanDate
import com.example.gb_my_app.utils.showSnackbar
import com.example.gb_my_app.utils.toVisibility
import com.example.gb_my_app.view_model.ActorViewModel

private const val ARG_ACTOR_ID = "ACTOR_ID"

class ActorFragment : Fragment() {

    companion object {
        fun newInstance(actorId: Int) = ActorFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_ACTOR_ID, actorId)
            }
        }
    }

    private val viewModel: ActorViewModel by lazy {
        ViewModelProvider(this).get(ActorViewModel::class.java)
    }

    private var paramActorID: Int? = null

    private var callbacks: MainFragment.Callbacks? = null

    // View binding
    private var viewBindingRef: ActorFragmentBinding? = null

    private val viewBinding get() = viewBindingRef!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.also {
            paramActorID = it.getInt(ARG_ACTOR_ID)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as MainFragment.Callbacks
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBindingRef = ActorFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.actorLiveData.observe(viewLifecycleOwner, ::render)
        viewModel.getActorById(paramActorID)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBindingRef = null
    }

    private fun render(appState: AppState) {
        when (appState) {
            is AppState.Failure -> viewBinding.also { vb ->
                callbacks?.onShowProgress(View.INVISIBLE)

                vb.root.showSnackbar("Ошибка") {
                    viewModel.getActorById(paramActorID)
                }
            }
            is AppState.Loading -> callbacks?.onShowProgress(View.VISIBLE)
            is AppState.Success -> viewBinding.also { vb ->
                val actor: Actor = (appState as AppState.ActorFetched).actor

                actor.also { a ->
                    vb.actorName.text = a.name
                    vb.actorBirthday.text = a.birthday.convertToHumanDate()
                    vb.actorPopularity.text = a.popularity.toString()
                }

                callbacks?.onShowProgress(View.INVISIBLE)
                vb.root toVisibility View.VISIBLE
            }
        }
    }
}
