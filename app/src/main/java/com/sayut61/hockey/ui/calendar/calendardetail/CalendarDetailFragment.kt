package com.sayut61.hockey.ui.calendar.calendardetail
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.sayut61.hockey.databinding.FragmentCalendarDetailBinding
import com.sayut61.hockey.domain.entities.GameFullInfo
import com.sayut61.hockey.ui.calendar.calendardetail.adapters_and_recyclerFragment.AwayTeamRecyclerFragment
import com.sayut61.hockey.ui.calendar.calendardetail.adapters_and_recyclerFragment.HomeTeamRecyclerFragment
import com.sayut61.hockey.ui.utils.loadImage
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class CalendarDetailFragment : Fragment() {
    private val viewModel: CalendarDetailViewModel by viewModels()
    private var _binding: FragmentCalendarDetailBinding? = null
    private val binding get() = _binding!!
    private val args: CalendarDetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentList = arrayListOf(
            HomeTeamRecyclerFragment.getInstance(args.game),
            AwayTeamRecyclerFragment.getInstance(args.game)
        )
        val adapter = CalendarDetailViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle)
        binding.calendarViewPager.adapter = adapter
        binding.calendarTabLayout.visibility = View.INVISIBLE
        binding.calendarViewPager.visibility = View.INVISIBLE
            TabLayoutMediator(binding.calendarTabLayout, binding.calendarViewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = "ХОЗЯЕВА"
                    1 -> tab.text = "ГОСТИ"
                }
            }.attach()
        //__________________________________________________________________________
        val gameGeneralInfo = args.game
        viewModel.refreshViewModel(gameGeneralInfo)
        viewModel.getGameInfo.observe(viewLifecycleOwner) {
            showFullInfo(it)
            if((it.gameState == "окончен")|| (it.gameState ==("идет"))){
                binding.calendarTabLayout.visibility = View.VISIBLE
                binding.calendarViewPager.visibility = View.VISIBLE
                binding.emptyListTextView.visibility = View.GONE
            }else{
                binding.calendarTabLayout.visibility = View.INVISIBLE
                binding.calendarViewPager.visibility = View.INVISIBLE
                binding.emptyListTextView.visibility = View.VISIBLE
            }
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            showError(it)
        }
        viewModel.progressBarLiveData.observe(viewLifecycleOwner){
            if(it == true){
                showProgressBar()
            }else hideProgressBar()
        }
    }
    private fun showFullInfo(gameFullInfo: GameFullInfo) {
        binding.dateAndTimeGameTextView.text = gameFullInfo.generalInfo.gameDate
        binding.homeTeamScoreTextView.text = gameFullInfo.goalsHomeTeam.toString()
        binding.awayTeamScoreTextView.text = gameFullInfo.goalsAwayTeam.toString()
        binding.homeTeamFullNameTextView.text = gameFullInfo.generalInfo.homeTeamNameFull
        binding.awayTeamFullNameTextView.text = gameFullInfo.generalInfo.awayTeamNameFull

        gameFullInfo.generalInfo.awayTeamLogo?.let { logoUrl ->
            loadImage(logoUrl, activity, binding.awayTeamDetailImageView)
        }
        gameFullInfo.generalInfo.homeTeamLogo?.let { logoUrl ->
            loadImage(logoUrl, activity, binding.homeTeamDetailImageView)
        }
        gameFullInfo.generalInfo.awayTeamLogo?.let { logoUrl ->
            loadImage(logoUrl, activity, binding.awayTeamImageView)
        }
        gameFullInfo.generalInfo.homeTeamLogo?.let { logoUrl ->
            loadImage(logoUrl, activity, binding.homeTeamImageView)
        }

        for (period in gameFullInfo.periods) {
            when (period.periodNumber) {
                1 -> {
                    binding.firstPeriodHomeTeamGoalTextView.text = period.homeTeam.goals.toString()
                    binding.firstPeriodAwayTeamGoalTextView.text = period.awayTeam.goals.toString()
                }
                2 -> {
                    binding.secondPeriodHomeTeamGoalTextView.text = period.homeTeam.goals.toString()
                    binding.secondPeriodAwayTeamGoalTextView.text = period.awayTeam.goals.toString()
                }
                3 -> {
                    binding.thirdPeriodHomeTeamGoalTextView.text = period.homeTeam.goals.toString()
                    binding.thirdPeriodAwayTeamGoalTextView.text = period.awayTeam.goals.toString()
                }
            }
        }
        binding.gameStatusTextView.text = gameFullInfo.gameState
    }

    private fun showProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
    }
    private fun hideProgressBar(){
        binding.progressBar.visibility = View.INVISIBLE
    }
    private fun showError(exception: Exception) {
        Toast.makeText(requireContext(), "Ошибка - ${exception.message}", Toast.LENGTH_LONG).show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}