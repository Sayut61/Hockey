package com.sayut61.hockey.ui.teams.teamdetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.sayut61.hockey.R
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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentTeamPlayersBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val teamId = arguments?.getInt(TeamStatisticDetailFragment.TEAM_ID_ARG) ?: throw  java.lang.Exception("Create me with get instance")
        Log.d("myLog", teamId.toString())
        viewModel.refreshPlayersFragment(teamId)
        viewModel.errorLiveData.observe(viewLifecycleOwner){
            showError(it)
        }
        viewModel.playersLiveData.observe(viewLifecycleOwner){
            showPlayers(it)
        }
        val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_drawable))
        binding.listPlayersRecyclerView.addItemDecoration(dividerItemDecoration)
    }
    private fun showPlayers(players: List<TeamPlayersInfo>){
        val adapter = TeamPlayersAdapter(players, this)
        binding.listPlayersRecyclerView.adapter = adapter
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
    companion object {
        private const val TEAM_ID_ARG = "team_id"
        fun getInstance(teamId: Int): TeamPlayersDetailFragment {
            val bundle = Bundle()
            bundle.putInt(TEAM_ID_ARG, teamId)
            val teamPlayersDetailFragment = TeamPlayersDetailFragment()
            teamPlayersDetailFragment.arguments = bundle
            return teamPlayersDetailFragment
        }
    }

}