package com.sayut61.hockey.ui.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sayut61.hockey.databinding.GameFavItemBinding
import com.sayut61.hockey.domain.entities.GameGeneralInfo
import com.sayut61.hockey.ui.utils.loadImage

interface GameFavoriteAdapterListener {
    fun onGameClick(gameGeneralInfo: GameGeneralInfo)
    fun onDeleteButtonClick(gameGeneralInfo: GameGeneralInfo)
}
class GameFavoriteAdapter(
    private val getGameInfo: List<GameGeneralInfo>,
    private val listener: GameFavoriteAdapterListener,
    private val activity: Activity?
) : RecyclerView.Adapter<GameFavoriteViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameFavoriteViewHolder {
        return GameFavoriteViewHolder(GameFavItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
    override fun onBindViewHolder(holder: GameFavoriteViewHolder, position: Int) {
        val calendar = getGameInfo[position]
        holder.itemView.setOnClickListener {
            listener.onGameClick(calendar)
        }
        holder.binding.deleteFavoriteButton.setOnClickListener {
            listener.onDeleteButtonClick(calendar)
        }
        holder.bind(calendar, activity)
    }
    override fun getItemCount(): Int {
        return getGameInfo.size
    }
}
class GameFavoriteViewHolder(val binding: GameFavItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(gameGeneralInfo: GameGeneralInfo, activity: Activity?) {
        binding.awayTeamTextView.text = gameGeneralInfo.awayTeamNameFull
        binding.homeTeamTextView.text = gameGeneralInfo.homeTeamNameFull
        binding.dateGameTextView.text = gameGeneralInfo.gameDate
        gameGeneralInfo.awayTeamLogo?.let { logoUrl ->
            loadImage(logoUrl, activity, binding.awayTeamImageView)
        }
        gameGeneralInfo.homeTeamLogo?.let { logoUrl ->
            loadImage(logoUrl, activity, binding.homeTeamImageView)
        }
    }
}