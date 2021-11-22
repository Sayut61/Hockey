package com.sayut61.hockey.ui.adapters

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sayut61.hockey.R
import com.sayut61.hockey.databinding.CalendarDaysItemBinding
import com.sayut61.hockey.domain.entities.GameFullInfo

data class CalendarDay(
    val number: Int,
    val name: String,
    val month: Int,
    val year: Int
)
interface CalendarDateAdapterListener {
    fun onDayClick(day: CalendarDay)
}
class CalendarDateAdapter(var days: List<CalendarDay>, val highlightIndex: Int,  val listener: CalendarDateAdapterListener) :
    RecyclerView.Adapter<CalendarDateViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarDateViewHolder {
        return CalendarDateViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.calendar_days_item, parent, false)
        )
    }
    override fun onBindViewHolder(holder: CalendarDateViewHolder, position: Int) {
        val day = days[position]
        holder.bind(day, listener, highlightIndex == position+1)
    }
    override fun getItemCount(): Int {
        return days.size
    }
}
class CalendarDateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val binding = CalendarDaysItemBinding.bind(itemView)
    fun bind(calendarDay: CalendarDay, listener: CalendarDateAdapterListener, highLight: Boolean = false) {
        binding.dayNumberTextView.text = calendarDay.number.toString()
        binding.nameDayTextView.text = calendarDay.name
        itemView.setOnClickListener {
            listener.onDayClick(calendarDay)
        }
        setHighLight(highLight)
    }
    private fun setHighLight(highLight: Boolean) {
        if (highLight)
            binding.dayNumberTextView.setTypeface(null, Typeface.BOLD)
        else
            binding.dayNumberTextView.setTypeface(null, Typeface.NORMAL)
    }
}