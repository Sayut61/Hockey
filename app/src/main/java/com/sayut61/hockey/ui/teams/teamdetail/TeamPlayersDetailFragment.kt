package com.sayut61.hockey.ui.teams.teamdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sayut61.hockey.databinding.FragmentTeamPlayersBinding
import com.sayut61.hockey.domain.entities.TeamPlayersInfo
import com.sayut61.hockey.ui.adapters.TeamPlayersAdapter
import com.sayut61.hockey.ui.adapters.TeamPlayersAdapterListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamPlayersDetailFragment(): Fragment(), TeamPlayersAdapterListener {
    private  val viewModel: TeamPlayersDetailViewModel by viewModels()
    private var _binding: FragmentTeamPlayersBinding? = null
    private val binding get() = _binding!!
    private val args: TeamDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentTeamPlayersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.refreshPlayersFragment(args.team.id)
        viewModel.progressBarLiveData.observe(viewLifecycleOwner){
            if (it == true){ showProgressBar() }
            else hideProgressBar()
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner){
            showError(it)
        }
        viewModel.playersLiveData.observe(viewLifecycleOwner){
            showPlayers(it)
        }
    }
    private fun showPlayers(players: List<TeamPlayersInfo>){
        val adapter = TeamPlayersAdapter(players, this)
        binding.listPlayersRecyclerView.adapter = adapter
    }
    private fun showProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
    }
    private fun hideProgressBar(){
        binding.progressBar.visibility = View.INVISIBLE
    }
    private fun showError(ex: Exception){
        Toast.makeText(requireContext(),"Ошибка - ${ex.message}", Toast.LENGTH_LONG).show()
    }

    override fun onPlayerClick(playersInfo: TeamPlayersInfo) {
        val action = TeamDetailFragmentDirections.actionTeamDetailFragmentToPlayerDetailInfoFragment(playersInfo.playerId)
        findNavController().navigate(action)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}