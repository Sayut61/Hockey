package com.sayut61.hockey.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sayut61.hockey.R
import com.sayut61.hockey.databinding.TeamItemBinding
import com.sayut61.hockey.domain.entities.Team
import com.sayut61.hockey.ui.utils.loadSvg
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


interface TeamAdapterListener{
    fun onTeamClick(team: Team)
}
class TeamsAdapter (private val getTeamName: List<Team>, private val listener: TeamAdapterListener):RecyclerView.Adapter<TeamsViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        return TeamsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.team_item, parent, false))
    }
    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        val team = getTeamName[position]
        holder.itemView.setOnClickListener{
            listener.onTeamClick(team)
        }
        holder.bind(team)
    }
    override fun getItemCount(): Int {
        return getTeamName.size
    }
}
class TeamsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    fun bind(team: Team){
        val binding = TeamItemBinding.bind(itemView)
        binding.teamNameTextView.text = team.teamName
        val url = team.urlLogoTeam
        url?.let{
            GlobalScope.launch {
                binding.logoImageView.loadSvg(it)
            }
        }
    }
}