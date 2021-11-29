package com.sayut61.hockey.ui.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sayut61.hockey.databinding.FavoritePlayerStatItemBinding
import com.sayut61.hockey.databinding.FragmentPlayerFavoriteBinding
import com.sayut61.hockey.domain.entities.PlayerGeneralInfo
import com.sayut61.hockey.domain.entities.PlayerStatisticsInfo
import com.sayut61.hockey.ui.utils.loadImage

interface FavoriteAdapterListener{
    fun deleteButtonClick(playerId: Int)
}
class PlayersFavoriteAdapter(
    val listener: FavoriteAdapterListener,
    val activity: Activity
): ListAdapter<PlayerStatisticsInfo, PlayersFavoriteViewHolder>(PlayersFavoriteDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayersFavoriteViewHolder {
        return PlayersFavoriteViewHolder(
            FavoritePlayerStatItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false))
    }
    override fun onBindViewHolder(holder: PlayersFavoriteViewHolder, position: Int) {
        val player = getItem(position)
        holder.binding.deleteImageButton.setOnClickListener {
            listener.deleteButtonClick(player.id)
        }
        holder.bind(player, activity)
    }
}
class PlayersFavoriteViewHolder(val binding: FavoritePlayerStatItemBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(player: PlayerStatisticsInfo, activity: Activity){
        binding.nameFavPlayerTextView.text = player.name
        player.photo.let { logoUrl ->
            if (logoUrl != null) {
                loadImage(logoUrl, activity, binding.photoFavPlayerImageView)
            }
        }
        binding.goalsTextView.text = player.goals.toString()
        binding.assistsTextView.text = player.assists.toString()
        binding.shotsTextView.text = player.shots.toString()
        binding.pointsTextView.text = player.points.toString()
        binding.hitsTextView.text = player.hits.toString()
        binding.blockTextView.text = player.blocked.toString()
        binding.plusMinusTextView.text = player.plusMinus.toString()
        binding.gamesTextView.text = player.games.toString()
        binding.PPTimeTextView.text = player.powerPlayTimeOnIce
        binding.timeOnIceFullTextView.text = player.timeOnIce
        binding.timeOnIcePerGameTextView.text = player.timeOnIcePerGame
        binding.PPGoalsTextView.text = player.powerPlayGoals.toString()
        binding.PPPointsTextView.text = player.powerPlayPoints.toString()
    }
}
class PlayersFavoriteDiffUtil: DiffUtil.ItemCallback<PlayerStatisticsInfo>(){
    override fun areItemsTheSame(
        oldItem: PlayerStatisticsInfo,
        newItem: PlayerStatisticsInfo
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: PlayerStatisticsInfo,
        newItem: PlayerStatisticsInfo
    ): Boolean {
        return oldItem == newItem
    }

}