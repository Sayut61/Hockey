package com.sayut61.hockey.ui.calendar.calendardetail.adapters_and_recyclerFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sayut61.hockey.databinding.AwayTeamPlayersItemBinding
import com.sayut61.hockey.domain.entities.GameFullInfo
import com.sayut61.hockey.domain.entities.PlayerNameAndNumber

class AwayTeamAdapter(val awayPlayers: List<PlayerNameAndNumber>): RecyclerView.Adapter<AwayTeamViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AwayTeamViewHolder {
        return AwayTeamViewHolder(AwayTeamPlayersItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    override fun onBindViewHolder(holder: AwayTeamViewHolder, position: Int) {
        val player = awayPlayers[position]
        holder.bind(player)
    }
    override fun getItemCount(): Int {
        return awayPlayers.size
    }
}
class AwayTeamViewHolder(val binding: AwayTeamPlayersItemBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(gameFullInfo: PlayerNameAndNumber){
        binding.nameTextView.text = gameFullInfo.name
        binding.numberTextView.text = gameFullInfo.number
    }
}