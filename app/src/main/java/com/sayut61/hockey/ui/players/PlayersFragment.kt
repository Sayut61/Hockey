package com.sayut61.hockey.ui.players

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sayut61.hockey.databinding.FragmentPlayersBinding
import com.sayut61.hockey.domain.entities.Player
import com.sayut61.hockey.ui.adapters.PlayersAdapter
import com.sayut61.hockey.ui.adapters.PlayersAdapterListener
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class PlayersFragment : Fragment(), PlayersAdapterListener {
    private  val viewModel: PlayersViewModel by viewModels()
    private var _binding: FragmentPlayersBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View { _binding = FragmentPlayersBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.addToFavorite()
        viewModel.refreshFragment()
        viewModel.errorLiveData.observe(viewLifecycleOwner){
            showError(it)
        }
        viewModel.listPlayerLiveData.observe(viewLifecycleOwner){
            showListPlayers(it)
        }
        viewModel.progressBarLiveData.observe(viewLifecycleOwner){
            if(it == true)showProgressBar()
            else hideProgressBar()
        }
    }
    override fun onPlayerClick(player: Player) {
        val action = PlayersFragmentDirections.actionPlayersFragmentToPlayerInfoFragment(player)
        findNavController().navigate(action)
    }
    private fun showError(exception: Exception){
        Toast.makeText(requireContext(), "Ошибка - ${exception.message}", Toast.LENGTH_LONG).show()
    }
    private fun showListPlayers(players: List<Player>){
        val adapter = PlayersAdapter(players, this, activity as Activity)
        binding.playersRecyclerView.adapter = adapter
    }
    private fun showProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
    }
    private fun hideProgressBar(){
        binding.progressBar.visibility = View.INVISIBLE
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}