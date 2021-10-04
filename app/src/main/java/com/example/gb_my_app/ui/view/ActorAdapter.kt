package com.example.gb_my_app.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gb_my_app.R
import com.example.gb_my_app.model.Actor
import com.example.gb_my_app.utils.setTextToView

class ActorAdapter(
    private val actorList: List<Actor>,
    private val callbacks: ActorsFragment.Callbacks?,

    ) : RecyclerView.Adapter<ActorAdapter.ActorHolder>() {

    inner class ActorHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(actor: Actor) {
            actor.apply {
                val mapOfTextViewIdAndText: Map<Int, String> = mapOf(
                    R.id.actor_name to name,
                    R.id.actor_popularity to popularity.toString(),
                )

                mapOfTextViewIdAndText.forEach { (resourceId, text) ->
                    itemView.setTextToView(resourceId, text)
                }

                itemView.setOnClickListener { callbacks?.onSelectActor(id) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.actors_fragment_list_item, parent, false)

        return ActorHolder(view)
    }

    override fun onBindViewHolder(holder: ActorHolder, position: Int) {
        val currentActor: Actor = actorList[position]

        holder.bind(currentActor)
    }

    override fun getItemCount() = actorList.size
}
