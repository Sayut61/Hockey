package com.sayut61.hockey.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sayut61.hockey.R
import com.sayut61.hockey.databinding.CalendarItemBinding
import com.sayut61.hockey.domain.entities.Game


interface CalendarAdapterListener{
    fun onCalendarClick(game: Game)
    fun onFavButtonClick(game: Game)
}
class CalendarAdapter(private val getGameInfo: List<Game>, private val listener: CalendarAdapterListener): RecyclerView.Adapter<CalendarViewHolder>(){
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
    fun bind(game: Game){
        binding.firstTeamTextView.text = game.homeTeamName
        binding.secondTeamTextView.text = game.awayTeamName
        binding.dateTimeTextView.text = game.gameDate

        binding.addToFavoriteButton.setImageResource(if(game.isInFavorite)
            R.drawable.ic_baseline_favorite_gameplus
        else
            R.drawable.ic_baseline_favorite_gameminus)
    }
}