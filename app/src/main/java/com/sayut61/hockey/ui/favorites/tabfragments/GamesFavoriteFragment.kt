package com.sayut61.hockey.ui.favorites.tabfragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.sayut61.hockey.databinding.FragmentGameFavoriteBinding
import com.sayut61.hockey.domain.entities.Game
import com.sayut61.hockey.ui.adapters.GameFavoriteAdapter
import com.sayut61.hockey.ui.adapters.GameFavoriteAdapterListener
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
@AndroidEntryPoint
class GamesFavoriteFragment : Fragment(), GameFavoriteAdapterListener{
    private val viewModel: GamesFavoriteFragmentViewModel by viewModels()
    private var _binding: FragmentGameFavoriteBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentGameFavoriteBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.gamesFavoriteLiveData.observe(viewLifecycleOwner){
            showGameInfo(it)
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner){
            showError(it)
        }
        viewModel.refreshFavoriteFragment()
    }

    fun showGameInfo(game: List<Game>){
        val adapter = GameFavoriteAdapter(game, this, activity as? Activity)
        binding.favoriteGameRecyclerView.adapter = adapter
    }
    override fun onGameClick(game: Game) {
        TODO("Not yet implemented")
    }

    override fun onDeleteButtonClick(game: Game) {
        viewModel.deleteFromFavorite(game)
    }

    private fun showError(ex: Exception){
        Toast.makeText(requireContext(),"Ошибка - ${ex.message}", Toast.LENGTH_LONG).show()
    }


}