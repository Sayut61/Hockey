package com.sayut61.hockey.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.sayut61.hockey.databinding.FragmentFavoriteBinding
import com.sayut61.hockey.ui.favorites.adapter.ViewPagerAdapter
import com.sayut61.hockey.ui.favorites.tabfragments.GamesFavoriteFragment
import com.sayut61.hockey.ui.favorites.tabfragments.PlayersFavoriteFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewPagerFavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentList = arrayListOf(
            GamesFavoriteFragment(),
            PlayersFavoriteFragment()
        )
        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        binding.viewPager2.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            when (position) {
                0 -> tab.text = "ИГРЫ"
                1 -> tab.text = "ИГРОКИ"
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}