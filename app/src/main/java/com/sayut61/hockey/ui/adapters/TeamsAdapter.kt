package com.sayut61.hockey.ui.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sayut61.hockey.R
import com.sayut61.hockey.databinding.TeamItemBinding
import com.sayut61.hockey.domain.entities.Team
import com.sayut61.hockey.ui.utils.loadImage
import com.sayut61.hockey.ui.utils.svgloader.SvgLoader


interface TeamAdapterListener {
    fun onTeamClick(team: Team)
}

class TeamsAdapter(
    private val getTeamName: List<Team>,
    private val listener: TeamAdapterListener,
    val activity: Activity?
) : RecyclerView.Adapter<TeamsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        return TeamsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.team_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        val team = getTeamName[position]
        holder.itemView.setOnClickListener {
            listener.onTeamClick(team)
        }
        holder.bind(team, activity)
    }

    override fun getItemCount(): Int {
        return getTeamName.size
    }
}

class TeamsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(team: Team, activity: Activity?) {
        val binding = TeamItemBinding.bind(itemView)
        binding.teamNameTextView.text = team.teamName
        team.urlLogoTeam?.let{logoUrl->
            loadImage(logoUrl, activity, binding.logoImageView)
        }
    }


}