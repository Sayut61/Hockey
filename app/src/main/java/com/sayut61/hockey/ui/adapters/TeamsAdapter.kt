package com.sayut61.hockey.ui.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sayut61.hockey.R
import com.sayut61.hockey.databinding.TeamItemBinding

import com.sayut61.hockey.domain.entities.TeamGeneralInfo
import com.sayut61.hockey.ui.utils.loadImage

interface TeamAdapterListener {
    fun onTeamClick(teamGeneralInfo: TeamGeneralInfo)
}

class TeamsAdapter(
    private val getTeamGeneralInfoName: List<TeamGeneralInfo>,
    private val listener: TeamAdapterListener,
    val activity: Activity?
) : RecyclerView.Adapter<TeamsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        return TeamsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.team_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        val team = getTeamGeneralInfoName[position]
        holder.itemView.setOnClickListener {
            listener.onTeamClick(team)
        }
        holder.bind(team, activity, position)
    }

    override fun getItemCount(): Int {
        return getTeamGeneralInfoName.size
    }
}

class TeamsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(teamGeneralInfo: TeamGeneralInfo, activity: Activity?, position: Int) {
        val binding = TeamItemBinding.bind(itemView)
        teamGeneralInfo.urlLogoTeam?.let { logoUrl ->
            loadImage(logoUrl, activity, binding.logoImageView)
        }
        binding.textView7.text = teamGeneralInfo.cityName
        binding.textView9.text = teamGeneralInfo.shortTeamName
//       val color = if(teamGeneralInfo.fullTeamName.equals("Chicago Blackhawks"))
//            R.color.black
//        else
//           android.R.color.transparent

//        val color = if (position % 4 == 0 || (position - 3) % 4 == 0)
//            R.color.design_default_color_primary_dark
//        else
//            R.color.black
//
//        binding.root.setBackgroundColor(
//            itemView.context.resources.getColor(
//                color,
//                itemView.context.theme
//            )
//        )
    }
}