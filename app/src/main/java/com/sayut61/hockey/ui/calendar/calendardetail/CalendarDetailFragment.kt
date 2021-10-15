package com.sayut61.hockey.ui.calendar.calendardetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.sayut61.hockey.databinding.FragmentCalendarDetailBinding
import com.sayut61.hockey.ui.calendar.calendardetail.adapters_and_recyclerFragment.AwayTeamRecyclerFragment
import com.sayut61.hockey.ui.calendar.calendardetail.adapters_and_recyclerFragment.HomeTeamRecyclerFragment
import com.sayut61.hockey.ui.calendar.calendardetail.adapters_and_recyclerFragment.ViewPagerAdapterGame
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalendarDetailFragment : Fragment() {
    private val viewModel: CalendarDetailViewModel by viewModels()
    private var _binding: FragmentCalendarDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCalendarDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentList = arrayListOf(
            AwayTeamRecyclerFragment(),
            HomeTeamRecyclerFragment()
        )
        val adapter = ViewPagerAdapterGame(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle)
        binding.calendarViewPager.adapter = adapter
        TabLayoutMediator(binding.calendarTabLayout, binding.calendarViewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "home"
                1 -> tab.text = "away"
            }
        }.attach()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}