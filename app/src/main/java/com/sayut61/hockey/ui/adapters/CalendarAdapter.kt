package com.sayut61.hockey.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sayut61.hockey.R
import com.sayut61.hockey.databinding.CalendarItemBinding
import com.sayut61.hockey.domain.entities.GameGeneralInfo


interface CalendarAdapterListener{
    fun onCalendarClick(gameGeneralInfo: GameGeneralInfo)
    fun onFavButtonClick(gameGeneralInfo: GameGeneralInfo)
}
class CalendarAdapter(private val getGameInfo: List<GameGeneralInfo>, private val listener: CalendarAdapterListener): RecyclerView.Adapter<CalendarViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        return CalendarViewHolder(CalendarItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val calendar = getGameInfo[position]
        holder.itemView.setOnClickListener{
            listener.onCalendarClick(calendar)
        }
        holder.binding.addToFavoriteButton.setOnClickListener {
            listener.onFavButtonClick(calendar)
        }
        holder.bind(calendar)
    }
    override fun getItemCount(): Int {
        return getGameInfo.size
    }
}
class CalendarViewHolder(val binding: CalendarItemBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(gameGeneralInfo: GameGeneralInfo){
        binding.firstTeamTextView.text = gameGeneralInfo.homeTeamNameFull
        binding.secondTeamTextView.text = gameGeneralInfo.awayTeamNameFull
        binding.dateTimeTextView.text = gameGeneralInfo.gameDate

        binding.addToFavoriteButton.setImageResource(if(gameGeneralInfo.isInFavoriteGame)
            R.drawable.ic_baseline_favorite_gameplus
        else
            R.drawable.ic_baseline_favorite_gameminus)
    }
}