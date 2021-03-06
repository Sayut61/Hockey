package com.sayut61.hockey.ui.calendar.calendardetail.calendar_detail_recycler_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sayut61.hockey.databinding.HomeTeamPlayersItemBinding
import com.sayut61.hockey.domain.entities.PlayerNameAndNumber
import com.sayut61.hockey.ui.utils.changePositionName

interface HomeTeamAdapterListener {
    fun onPlayerClick(player: PlayerNameAndNumber)
}

class HomeTeamAdapter(
    private val homePlayers: List<PlayerNameAndNumber>,
    val listener: HomeTeamAdapterListener
) : RecyclerView.Adapter<HomeTeamViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeTeamViewHolder {
        return HomeTeamViewHolder(
            HomeTeamPlayersItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HomeTeamViewHolder, position: Int) {
        val player = homePlayers[position]
        holder.itemView.setOnClickListener {
            listener.onPlayerClick(player)
        }
        holder.bind(player)
    }

    override fun getItemCount(): Int {
        return homePlayers.size
    }
}

class HomeTeamViewHolder(val binding: HomeTeamPlayersItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(gameFullInfo: PlayerNameAndNumber) {
        binding.nameTextView.text = gameFullInfo.name
        binding.numberTextView.text = gameFullInfo.number
        binding.typePositionTextView.text = changePositionName(gameFullInfo.typePosition)
    }
}