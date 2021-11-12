package com.sayut61.hockey.ui.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sayut61.hockey.R
import com.sayut61.hockey.databinding.PlayersItemBinding
import com.sayut61.hockey.domain.entities.PlayerGeneralInfo
import com.sayut61.hockey.ui.utils.loadImage

interface PlayersAdapterListener{
    fun onPlayerClick(playerGeneralInfo: PlayerGeneralInfo)
    fun onFavoriteButtonClick(playerId: PlayerGeneralInfo)
}
class PlayersAdapter(private val playerGeneralInfo: List<PlayerGeneralInfo>,
                     private val listener: PlayersAdapterListener,
                     val activity: Activity?) : RecyclerView.Adapter<PlayersViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayersViewHolder {
        return PlayersViewHolder(PlayersItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    override fun onBindViewHolder(holder: PlayersViewHolder, position: Int) {
        val player = playerGeneralInfo[position]
        holder.itemView.setOnClickListener {
            listener.onPlayerClick(player)
        }
        holder.bind(player, activity, listener)
    }
    override fun getItemCount(): Int {
        return playerGeneralInfo.size
    }
}
class PlayersViewHolder(val binding: PlayersItemBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(playerGeneralInfo: PlayerGeneralInfo, activity: Activity?, listener: PlayersAdapterListener){
        binding.playerNumberTextView.text = playerGeneralInfo.jerseyNumber.toString()
        binding.playerFullNameTextView.text = playerGeneralInfo.fullName
        playerGeneralInfo.logo?.let{ logoUrl->
            loadImage(logoUrl, activity, binding.logoPlayerImageView)
        }
        setIsInFavoriteButton(playerGeneralInfo.isInFavorite)

        binding.addToFavoriteImageButton.setOnClickListener {
            listener.onFavoriteButtonClick(playerGeneralInfo)
            setIsInFavoriteButton(!playerGeneralInfo.isInFavorite)
        }
    }

    private fun setIsInFavoriteButton(isInFavorite: Boolean) {
        binding.addToFavoriteImageButton.setImageResource(
            if (isInFavorite)
                R.drawable.ic_baseline_favorite_gameplus
            else
                R.drawable.ic_baseline_favorite_gameminus
        )
    }
}