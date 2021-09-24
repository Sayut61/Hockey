package com.sayut61.hockey.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sayut61.hockey.R
import com.sayut61.hockey.databinding.CalendarItemBinding
import com.sayut61.hockey.domain.entities.Calendar

interface CalendarAdapterListener{
    fun onCalendarClick(game: Calendar)
}
class CalendarAdapter(private val getCalendarInfo: List<Calendar>, val listener: CalendarAdapterListener): RecyclerView.Adapter<CalendarViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        return CalendarViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.calendar_item, parent, false))
    }
    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
       val calendar = getCalendarInfo[position]
        holder.itemView.setOnClickListener{
            listener.onCalendarClick(calendar)
        }
        holder.bind(calendar)
    }
    override fun getItemCount(): Int {
        return getCalendarInfo.size
    }
}
class CalendarViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    fun bind(game: Calendar){
        val binding = CalendarItemBinding.bind(itemView)
        binding.firstTeamTextView.text = game.homeTeamName
        binding.secondTeamTextView.text = game.awayTeamName
        binding.dateTimeTextView.text = game.gameDate
    }
}