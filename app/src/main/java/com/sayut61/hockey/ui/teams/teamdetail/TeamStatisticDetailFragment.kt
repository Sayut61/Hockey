package com.sayut61.hockey.ui.teams.teamdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.sayut61.hockey.databinding.FragmentTeamDetailBinding
import com.sayut61.hockey.databinding.FragmentTeamStatisticsBinding
import com.sayut61.hockey.domain.entities.TeamFullInfo
import com.sayut61.hockey.ui.favorites.adapter.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class TeamStatisticDetailFragment: Fragment() {
    private  val viewModel: TeamStatisticDetailViewModel by viewModels()
    private var _binding: FragmentTeamStatisticsBinding? = null
    private val binding get() = _binding!!
    private val args: TeamDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentTeamStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.refreshStatFragment(args.team.id)

        viewModel.errorLiveData.observe(viewLifecycleOwner){
            showError(it)
        }
        viewModel.progressBarLiveData.observe(viewLifecycleOwner){
            if (it == true){ showProgressBar() }
            else hideProgressBar()
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
    private fun showProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
    }
    private fun hideProgressBar(){
        binding.progressBar.visibility = View.INVISIBLE
    }
    private fun showError(ex: Exception){
        Toast.makeText(requireContext(),"Ошибка - ${ex.message}", Toast.LENGTH_LONG).show()
    }
}