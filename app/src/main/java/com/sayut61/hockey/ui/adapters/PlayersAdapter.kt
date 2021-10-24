package com.sayut61.hockey.ui.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sayut61.hockey.databinding.FragmentPlayersBinding
import com.sayut61.hockey.domain.entities.Players

interface PlayersAdapterListener{
    fun onPlayerClick(player: Players)
}
class PlayersAdapter(val players: List<Players>, val listener: PlayersAdapterListener, val activity: Activity?): RecyclerView.Adapter<PlayersViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayersViewHolder {
        return PlayersViewHolder(FragmentPlayersBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PlayersViewHolder, position: Int) {
        val player = players[position]
        holder.itemView.setOnClickListener {
            listener.onPlayerClick(player)
        }
        holder.bind(player, activity)
    }

    override fun getItemCount(): Int {
        return players.size
    }

}
class PlayersViewHolder(val binding: FragmentPlayersBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(player: Players, activity: Activity?){

    }
}