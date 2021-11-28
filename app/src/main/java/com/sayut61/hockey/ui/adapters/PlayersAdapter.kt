package com.sayut61.hockey.ui.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sayut61.hockey.R
import com.sayut61.hockey.databinding.PlayersItemBinding
import com.sayut61.hockey.domain.entities.PlayerGeneralInfo
import com.sayut61.hockey.ui.utils.loadImage

interface PlayersAdapterListener {
    fun onPlayerClick(playerGeneralInfo: PlayerGeneralInfo)
    fun onFavoriteButtonClick(playerId: PlayerGeneralInfo)
}

class PlayersAdapter(
    private val listener: PlayersAdapterListener,
    val activity: Activity?
) : ListAdapter<PlayerGeneralInfo, PlayersViewHolder>(PlayerGeneralInfoDiffUtil()) {
    var favoriteClickPlayer: PlayerGeneralInfo? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayersViewHolder {
        return PlayersViewHolder(
            PlayersItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PlayersViewHolder, position: Int) {
        val player = getItem(position)

        holder.itemView.setOnClickListener {
            listener.onPlayerClick(player)
        }
        holder.bind(player, activity)
        if (player.playerId != favoriteClickPlayer?.playerId) {
            holder.binding.addToFavoriteImageButton.animation =
                AnimationUtils.loadAnimation(holder.itemView.context, R.anim.recycler_anim2_1)
            holder.binding.logoPlayerImageView.animation =
                AnimationUtils.loadAnimation(holder.itemView.context, R.anim.recycler_anim2_2)
        }
        holder.binding.addToFavoriteImageButton.setOnClickListener {
            listener.onFavoriteButtonClick(player)
            holder.setIsInFavoriteButton(!player.isInFavorite)
            favoriteClickPlayer = player
        }

    }
}

class PlayersViewHolder(val binding: PlayersItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        playerGeneralInfo: PlayerGeneralInfo,
        activity: Activity?,
    ) {
        binding.playerNumberTextView.text = playerGeneralInfo.jerseyNumber.toString()
        binding.playerFullNameTextView.text = playerGeneralInfo.fullName


        playerGeneralInfo.logo?.let { logoUrl ->
            loadImage(logoUrl, activity, binding.logoPlayerImageView)
        }
        setIsInFavoriteButton(playerGeneralInfo.isInFavorite)
    }

    fun setIsInFavoriteButton(isInFavorite: Boolean) {
        binding.addToFavoriteImageButton.setImageResource(
            if (isInFavorite)
                R.drawable.ic_baseline_favorite_gameplus
            else
                R.drawable.ic_baseline_favorite_gameminus
        )
    }
}

class PlayerGeneralInfoDiffUtil : DiffUtil.ItemCallback<PlayerGeneralInfo>() {
    override fun areItemsTheSame(oldItem: PlayerGeneralInfo, newItem: PlayerGeneralInfo): Boolean {
        return oldItem.playerId == newItem.playerId
    }

    override fun areContentsTheSame(
        oldItem: PlayerGeneralInfo,
        newItem: PlayerGeneralInfo
    ): Boolean {
        return oldItem == newItem
    }

}