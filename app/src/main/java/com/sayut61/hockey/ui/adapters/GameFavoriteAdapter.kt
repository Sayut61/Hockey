package com.sayut61.hockey.ui.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sayut61.hockey.databinding.GameFavItemBinding
import com.sayut61.hockey.domain.entities.GameFullInfo
import com.sayut61.hockey.domain.entities.GameGeneralInfo
import com.sayut61.hockey.ui.utils.loadImage

interface GameFavoriteAdapterListener {
    fun onGameClick(gameGeneralInfo: GameGeneralInfo)
    fun onDeleteButtonClick(gameGeneralInfo: GameGeneralInfo)
}
class GameFavoriteAdapter(
    private val gamesList: List<GameFullInfo>,
    private val listener: GameFavoriteAdapterListener,
    private val activity: Activity?
) : RecyclerView.Adapter<GameFavoriteViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameFavoriteViewHolder {
        return GameFavoriteViewHolder(GameFavItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
    override fun onBindViewHolder(holder: GameFavoriteViewHolder, position: Int) {
        val gameInfo = gamesList[position]
        holder.itemView.setOnClickListener {
            listener.onGameClick(gameInfo.generalInfo)
        }
        holder.binding.deleteFavoriteButton.setOnClickListener {
            listener.onDeleteButtonClick(gameInfo.generalInfo)
        }
        holder.bind(gameInfo, activity)
    }
    override fun getItemCount(): Int {
        return gamesList.size
    }
}
class GameFavoriteViewHolder(val binding: GameFavItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(gameFullInfo: GameFullInfo, activity: Activity?) {
        binding.awayTeamTextView.text = gameFullInfo.generalInfo.awayTeamNameFull
        binding.homeTeamTextView.text = gameFullInfo.generalInfo.homeTeamNameFull
        binding.dateGameTextView.text = gameFullInfo.generalInfo.gameDate
        binding.gameStatusTextView.text = gameFullInfo.gameState
        binding.awayTeamScoreTextView.text = gameFullInfo.goalsAwayTeam.toString()
        binding.homeTeamScoreTextView.text = gameFullInfo.goalsHomeTeam.toString()
        gameFullInfo.generalInfo.awayTeamLogo?.let { logoUrl ->
            loadImage(logoUrl, activity, binding.awayTeamImageView)
        }
        gameFullInfo.generalInfo.homeTeamLogo?.let { logoUrl ->
            loadImage(logoUrl, activity, binding.homeTeamImageView)
        }
    }
}