package com.sayut61.hockey.ui.favorites.tabfragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.sayut61.hockey.databinding.FragmentPlayerFavoriteBinding
import com.sayut61.hockey.domain.entities.PlayerGeneralInfo
import com.sayut61.hockey.ui.adapters.FavoriteAdapterListener
import com.sayut61.hockey.ui.adapters.PlayersFavoriteAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class PlayersFavoriteFragment : Fragment(), FavoriteAdapterListener {
    private val viewModel: PlayersFavoriteViewModel by viewModels()
    private var _binding: FragmentPlayerFavoriteBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPlayerFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.playersFavoriteLiveData.observe(viewLifecycleOwner){
            val adapter = PlayersFavoriteAdapter(it, this, activity as Activity)
            binding.playerStatisticsRecyclerView.adapter = adapter
        }
        viewModel.refreshFavoriteFragment()
        viewModel.progressBarLiveData.observe(viewLifecycleOwner){
            if (it == true) showProgressBar()
            else hideProgressBar()
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner){
            showError(it)
        }
    }
    override fun deleteButtonClick(playerId: Int) {
        viewModel.deleteFromFavorite(playerId)
    }
    private fun showProgressBar(){
        binding.progressBar.visibility = ProgressBar.VISIBLE
    }
    private fun hideProgressBar(){
        binding.progressBar.visibility = ProgressBar.INVISIBLE
    }
    private fun showError(exception: Exception){
        Toast.makeText(requireContext(),"Ошибка: ${exception.message}", Toast.LENGTH_LONG).show()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}