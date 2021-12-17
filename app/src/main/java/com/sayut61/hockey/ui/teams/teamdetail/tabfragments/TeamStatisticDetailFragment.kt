package com.sayut61.hockey.ui.teams.teamdetail.tabfragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sayut61.hockey.databinding.FragmentTeamStatisticsBinding
import com.sayut61.hockey.domain.entities.TeamFullInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamStatisticDetailFragment(): Fragment() {
    private  val viewModel: TeamStatisticDetailViewModel by viewModels()
    private var _binding: FragmentTeamStatisticsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentTeamStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val teamId = arguments?.getInt(TEAM_ID_ARG) ?: throw  java.lang.Exception("Create me with get instance")
        Log.d("myLog", teamId.toString())
        viewModel.refreshStatFragment(teamId)

        viewModel.errorLiveData.observe(viewLifecycleOwner){
            showError(it)
        }
        viewModel.teamStatisticLiveData.observe(viewLifecycleOwner){
            showStatInfo(it)
        }
    }

    private fun showStatInfo(fullInfo: TeamFullInfo){
        binding.gamesPlayedTextView.text = fullInfo.gamesPlayed.toString()
        binding.goalsAgainstPerGameTextView.text = fullInfo.goalsAgainstPerGame.toString()
        binding.goalsPerGameTextView.text = fullInfo.goalsPerGame.toString()
        binding.lossesTextView.text = fullInfo.losses.toString()
        binding.ptsTextView.text = fullInfo.pts.toString()
        binding.winsTextView.text = fullInfo.wins.toString()
        binding.shotsPerGameTextView.text = fullInfo.shotsPerGame.toString()
        binding.shotsAllowedTextView.text = fullInfo.shotsAllowed.toString()
        binding.powerPlayGoalsAgainstTextView.text = fullInfo.powerPlayGoalsAgainst.toString()
        binding.powerPlayGoalsTextView.text = fullInfo.powerPlayGoals.toString()
        binding.powerPlayPercentageTextView.text = fullInfo.powerPlayPercentage
        binding.powerPlayOpportunitiesTextView.text = fullInfo.powerPlayOpportunities.toString()
        binding.placeGoalsAgainstPerGameTextView.text = fullInfo.placeGoalsAgainstPerGame
        binding.placeGoalsPerGameTextView.text = fullInfo.placeGoalsPerGame
        binding.placeOnLossesTextView.text = fullInfo.placeOnLosses
        binding.placeOnPtsTextView.text = fullInfo.placeOnPts
        binding.placeOnWinsTextView.text = fullInfo.placeOnWins
    }
    private fun showError(ex: Exception){
        Toast.makeText(requireContext(),"Ошибка - ${ex.message}", Toast.LENGTH_LONG).show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TEAM_ID_ARG = "team_id"
        fun getInstance(teamId: Int): TeamStatisticDetailFragment {
            val bundle = Bundle()
            bundle.putInt(TEAM_ID_ARG, teamId)
            val teamStatisticDetailFragment = TeamStatisticDetailFragment()
            teamStatisticDetailFragment.arguments = bundle
            return teamStatisticDetailFragment
        }
    }
}