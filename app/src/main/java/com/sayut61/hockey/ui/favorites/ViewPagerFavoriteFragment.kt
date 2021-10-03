package com.sayut61.hockey.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MediatorLiveData
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sayut61.hockey.R
import com.sayut61.hockey.databinding.FragmentFavoriteBinding
import com.sayut61.hockey.ui.favorites.adapter.ViewPagerAdapter
import com.sayut61.hockey.ui.favorites.tabfragments.GameFavoriteFragment
import com.sayut61.hockey.ui.favorites.tabfragments.PlayerFavoriteFragment
import com.sayut61.hockey.ui.favorites.tabfragments.TeamFavoriteFragment


class ViewPagerFavoriteFragment: Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentList = arrayListOf(
            GameFavoriteFragment(),
            PlayerFavoriteFragment(),
            TeamFavoriteFragment()
        )
        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        binding.viewPager2.adapter = adapter
        TabLayoutMediator(binding.tabLayout.findViewById(R.id.tabLayout), binding.viewPager2.findViewById(R.id.viewPager2)) { tab, position ->
            when(position){
                0->{
                    tab.text = "GAMES"
                }
                1->{
                    tab.text = "PLAYERS"
                }
                2->{
                    tab.text = "TEAMS"
                }
            }
        }.attach()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}