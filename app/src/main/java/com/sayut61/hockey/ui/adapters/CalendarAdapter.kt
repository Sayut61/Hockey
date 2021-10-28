package com.sayut61.hockey.ui.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sayut61.hockey.R
import com.sayut61.hockey.databinding.CalendarItemBinding
import com.sayut61.hockey.domain.entities.GameFullInfo
import com.sayut61.hockey.ui.utils.loadImage


interface CalendarAdapterListener{
    fun onCalendarClick(gameFullInfo: GameFullInfo)
    fun onFavButtonClick(gameFullInfo: GameFullInfo)
}
class CalendarAdapter(
    private val getGameFullInfo: List<GameFullInfo>,
    private val listener: CalendarAdapterListener,
    private val activity: Activity?
    ): RecyclerView.Adapter<CalendarViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        return CalendarViewHolder(CalendarItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val calendar = getGameFullInfo[position]
        holder.itemView.setOnClickListener{
            listener.onCalendarClick(calendar)
        }
        holder.binding.addToFavoriteButton.setOnClickListener {
            listener.onFavButtonClick(calendar)
        }
        holder.bind(calendar, activity)
    }
    override fun getItemCount(): Int {
        return getGameFullInfo.size
    }
}
class CalendarViewHolder(val binding: CalendarItemBinding):
    RecyclerView.ViewHolder(binding.root){
    fun bind(gameFullInfo: GameFullInfo, activity: Activity?){
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
        binding.addToFavoriteButton.setImageResource(if(gameFullInfo.generalInfo.isInFavoriteGame)
            R.drawable.ic_baseline_favorite_gameplus
        else
            R.drawable.ic_baseline_favorite_gameminus)
    }
}