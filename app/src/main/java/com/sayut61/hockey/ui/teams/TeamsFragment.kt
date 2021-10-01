package com.sayut61.hockey.ui.teams

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.sayut61.hockey.databinding.FragmentTeamsBinding
import com.sayut61.hockey.domain.entities.Team
import com.sayut61.hockey.ui.adapters.TeamAdapterListener
import com.sayut61.hockey.ui.adapters.TeamsAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class TeamsFragment : Fragment(), TeamAdapterListener {
    private  val teamsViewModel: TeamsViewModel by viewModels()
    private var _binding: FragmentTeamsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        _binding = FragmentTeamsBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewTeams.layoutManager = GridLayoutManager(requireContext(), 2)

        teamsViewModel.refreshTeamsFragment()

        teamsViewModel.teamInfoLiveData.observe(viewLifecycleOwner){
            showTeamsNames(it)
        }

        teamsViewModel.errorLiveData.observe(viewLifecycleOwner){
            showError(it)
        }
    }
    private fun showError(exception: Exception){
        Toast.makeText(requireContext(), "Ошибка - ${exception.message}", Toast.LENGTH_LONG).show()
    }

    override fun onTeamClick(team: Team) {
        val action = TeamsFragmentDirections.actionTeamsFragmentToTeamDetailFragment(team)
        findNavController().navigate(action)
    }

    private fun showTeamsNames(team: List<Team>){
        val adapter = TeamsAdapter(team, this, activity as? Activity)
        binding.recyclerViewTeams.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}