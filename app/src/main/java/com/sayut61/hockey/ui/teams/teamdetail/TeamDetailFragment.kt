package com.sayut61.hockey.ui.teams.teamdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.sayut61.hockey.databinding.FragmentTeamDetailBinding
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teams.FullInfoByTeam
import com.sayut61.hockey.domain.entities.TeamFullInfo
import com.sayut61.hockey.ui.favorites.adapter.ViewPagerAdapter
import com.sayut61.hockey.ui.utils.loadImage
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class TeamDetailFragment : Fragment() {
    private val viewModel: TeamDetailViewModel by viewModels()
    private var _binding: FragmentTeamDetailBinding? = null
    private val binding get() = _binding!!
    private val args: TeamDetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentList = arrayListOf(
            TeamStatisticDetailFragment.getInstance(args.team.id),
            TeamPlayersDetailFragment.getInstance(args.team.id)
        )
        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        binding.teamViewPager2.adapter = adapter
        TabLayoutMediator(binding.teamTabLayout, binding.teamViewPager2) { tab, position ->
            when (position) {
                0 -> tab.text = "СТАТИСТИКА"
                1 -> tab.text = "ИГРОКИ"
            }
        }.attach()

        //----------------------------------------------------------------

        viewModel.refreshTeamDetailViewModel(args.team.id)

        viewModel.teamDetailLiveData.observe(viewLifecycleOwner) {
            showTeamDetailInfo(it)
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            showError(it)
        }
        viewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            if (it == true) {
                showProgressBar()
            } else hideProgressBar()
        }
    }

    private fun showTeamDetailInfo(fullInfo: TeamFullInfo) {
        binding.teamFullNameTextView.text = fullInfo.teamGeneralInfo.fullTeamName
        binding.firstYearOfPlayTextView.text = fullInfo.firstYearOfPlay.toString()
        fullInfo.teamGeneralInfo.urlLogoTeam.let { logoUrl ->
            if (logoUrl != null) {
                loadImage(logoUrl, activity, binding.teamImageView)
            }
        }
    }
    private fun showProgressBar() { binding.progressBar.visibility = View.VISIBLE }
    private fun hideProgressBar() { binding.progressBar.visibility = View.INVISIBLE }
    private fun showError(ex: Exception) {
        Toast.makeText(requireContext(), "Ошибка - ${ex.message}", Toast.LENGTH_LONG).show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}