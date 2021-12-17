package com.sayut61.hockey.ui.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sayut61.hockey.R
import com.sayut61.hockey.databinding.CalendarItemBinding
import com.sayut61.hockey.domain.entities.GameFullInfo
import com.sayut61.hockey.ui.utils.loadImage


interface CalendarAdapterListener {
    fun onCalendarClick(gameFullInfo: GameFullInfo)
    fun onFavButtonClick(gameFullInfo: GameFullInfo)
}

class CalendarAdapter(
    private val listener: CalendarAdapterListener,
    private val activity: Activity?
) : ListAdapter<GameFullInfo, CalendarViewHolder>(CalendarDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        return CalendarViewHolder(
            CalendarItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val calendar = getItem(position)
        holder.itemView.setOnClickListener {
            listener.onCalendarClick(calendar)
        }
        holder.bind(calendar, activity, listener)
    }
}

class CalendarViewHolder(val binding: CalendarItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(gameFullInfo: GameFullInfo, activity: Activity?, listener: CalendarAdapterListener) {
        binding.firstTeamTextView.text = gameFullInfo.generalInfo.homeTeamNameFull
        binding.secondTeamTextView.text = gameFullInfo.generalInfo.awayTeamNameFull
        binding.dateTimeTextView.text = gameFullInfo.generalInfo.gameDate
        binding.firstTeamGoalsTextView.text = gameFullInfo.goalsHomeTeam.toString()
        binding.secondTeamGoalsTextView.text = gameFullInfo.goalsAwayTeam.toString()
        binding.gameStatusTextView.text = gameFullInfo.gameState
        gameFullInfo.generalInfo.awayTeamLogo?.let { logoUrl ->
            loadImage(logoUrl, activity, binding.secondTeamImageView2)
        }
        gameFullInfo.generalInfo.homeTeamLogo?.let { logoUrl ->
            loadImage(logoUrl, activity, binding.firstTeamImageView)
        }
        setIsInFavoriteButton(gameFullInfo.generalInfo.isInFavoriteGame)
        binding.addToFavoriteButton.setOnClickListener {
            listener.onFavButtonClick(gameFullInfo)
            setIsInFavoriteButton(!gameFullInfo.generalInfo.isInFavoriteGame)
        }
    }

    private fun setIsInFavoriteButton(isInFavorite: Boolean) {
        binding.addToFavoriteButton.setImageResource(
            if (isInFavorite)
                R.drawable.ic_baseline_favorite_gameplus
            else
                R.drawable.ic_baseline_favorite_gameminus
        )
    }
}

class CalendarDiffUtil : DiffUtil.ItemCallback<GameFullInfo>() {
    override fun areItemsTheSame(oldItem: GameFullInfo, newItem: GameFullInfo): Boolean {
        return oldItem.generalInfo.gameId == newItem.generalInfo.gameId
    }

    override fun areContentsTheSame(oldItem: GameFullInfo, newItem: GameFullInfo): Boolean {
        return oldItem == newItem
    }

}