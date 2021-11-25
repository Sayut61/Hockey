package com.sayut61.hockey.ui.players

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sayut61.hockey.R
import com.sayut61.hockey.databinding.FragmentPlayersBinding
import com.sayut61.hockey.domain.entities.PlayerGeneralInfo
import com.sayut61.hockey.ui.adapters.PlayersAdapter
import com.sayut61.hockey.ui.adapters.PlayersAdapterListener
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class PlayersFragment : Fragment(), PlayersAdapterListener {
    private val viewModel: PlayersViewModel by viewModels()
    private var _binding: FragmentPlayersBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayersBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.refreshFragment()
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            showError(it)
        }
        viewModel.listPlayersLiveData.observe(viewLifecycleOwner) {
            showListPlayers(it)
        }
        viewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            if (it == true) showProgressBar()
            else hideProgressBar()
        }
    }

    override fun onPlayerClick(playerGeneralInfo: PlayerGeneralInfo) {
        val action = PlayersFragmentDirections.actionPlayersFragmentToPlayerInfoFragment(playerGeneralInfo.playerId)
        findNavController().navigate(action)
    }

    override fun onFavoriteButtonClick(playerGeneralInfo: PlayerGeneralInfo) {
        viewModel.onFavoriteClick(playerGeneralInfo)
    }
    private fun showError(exception: Exception) {
        Toast.makeText(requireContext(), "Ошибка - ${exception.message}", Toast.LENGTH_LONG).show()
    }

    private fun showListPlayers(playerGeneralInfo: List<PlayerGeneralInfo>) {
        val adapter = PlayersAdapter(playerGeneralInfo, this, activity as Activity)
        binding.playersRecyclerView.adapter = adapter
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.top_menu, menu)
        val itemSearchView = menu.findItem(R.id.searchView)
        val searchView = itemSearchView.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(text: String?): Boolean {
                viewModel.changeFilter(text ?: "")
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}