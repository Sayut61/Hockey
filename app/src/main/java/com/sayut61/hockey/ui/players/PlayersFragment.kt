package com.sayut61.hockey.ui.players

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.*
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

@AndroidEntryPoint
class PlayersFragment : Fragment(), PlayersAdapterListener {
    private val viewModel: PlayersViewModel by viewModels()
    private var _binding: FragmentPlayersBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PlayersAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayersBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.refreshFragment()
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            showError(it)
        }
        adapter = PlayersAdapter(this, activity as Activity)
        binding.playersRecyclerView.adapter = adapter
        binding.playersRecyclerView.itemAnimator = null
        viewModel.listPlayersLiveData.observe(viewLifecycleOwner) { playersGeneralInfo ->
            adapter.submitList(playersGeneralInfo)
        }
        viewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            if (it == true) showProgressBar()
            else hideProgressBar()
        }
    }

    override fun onPlayerClick(playerGeneralInfo: PlayerGeneralInfo) {
        val action =
            PlayersFragmentDirections.actionPlayersFragmentToPlayerInfoFragment(playerGeneralInfo.playerId)
        findNavController().navigate(action)
    }

    override fun onFavoriteButtonClick(playerGeneralInfo: PlayerGeneralInfo) {
        viewModel.onFavoriteClick(playerGeneralInfo)
    }

    private fun showError(exception: Exception) {
        Toast.makeText(requireContext(), "???????????? - ${exception.message}", Toast.LENGTH_LONG).show()
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