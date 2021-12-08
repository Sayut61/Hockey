package com.sayut61.hockey.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sayut61.hockey.databinding.TeamPlayersItemBinding
import com.sayut61.hockey.domain.entities.TeamPlayersInfo

interface TeamPlayersAdapterListener{
    fun onPlayerClick(playersInfo: TeamPlayersInfo)
}

class TeamPlayersAdapter(val players: List<TeamPlayersInfo>, val listener: TeamPlayersAdapterListener): RecyclerView.Adapter<TeamPlayersViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamPlayersViewHolder {
        return TeamPlayersViewHolder(TeamPlayersItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    override fun onBindViewHolder(holder: TeamPlayersViewHolder, position: Int) {
        val player = players[position]
        holder.itemView.setOnClickListener {
            listener.onPlayerClick(player)
        }
        holder.bind(player)
    }
    override fun getItemCount(): Int {
        return players.size
    }
}
class TeamPlayersViewHolder(val binding: TeamPlayersItemBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(player: TeamPlayersInfo){
        binding.namePlayerTextView.text = player.fullName
        binding.numberPlayerTextView.text = player.jerseyNumber.toString()
        binding.typePlayerTextView.text = when(player.type){
            "Defenseman" -> "Защитник"
            "Forward" -> "Нападающий"
            "Goalie" -> "Вратарь"
            else -> ""
        }
    }
}