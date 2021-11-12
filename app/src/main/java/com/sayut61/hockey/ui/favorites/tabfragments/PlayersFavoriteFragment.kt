package com.sayut61.hockey.ui.favorites.tabfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.sayut61.hockey.R
import com.sayut61.hockey.databinding.FragmentGameFavoriteBinding
import com.sayut61.hockey.databinding.FragmentPlayerFavoriteBinding
import com.sayut61.hockey.ui.adapters.PlayersAdapterListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayersFavoriteFragment : Fragment(){
    private val viewModel: PlayersFavoriteViewModel by viewModels()
    private var _binding: FragmentPlayerFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPlayerFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}