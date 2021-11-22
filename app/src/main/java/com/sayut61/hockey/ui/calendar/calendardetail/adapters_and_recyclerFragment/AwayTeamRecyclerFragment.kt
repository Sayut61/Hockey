package com.sayut61.hockey.ui.calendar.calendardetail.adapters_and_recyclerFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.sayut61.hockey.databinding.FragmentAwayTeamPlayersBinding
import com.sayut61.hockey.ui.calendar.calendardetail.CalendarDetailFragmentArgs
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class AwayTeamRecyclerFragment: Fragment() {
    private val viewModel: AwayTeamRecyclerViewModel by viewModels()
    private var _binding: FragmentAwayTeamPlayersBinding? = null
    private val binding get() = _binding!!
    private val args: CalendarDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAwayTeamPlayersBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gameGeneralInfo = args.game
        viewModel.refreshFragment(gameGeneralInfo)
        viewModel.awayPlayersLiveData.observe(viewLifecycleOwner){
            val adapter = AwayTeamAdapter(it)
            binding.awayTeamRecyclerView.adapter = adapter
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner){
            showError(it)
        }
        viewModel.progressBarLiveData.observe(viewLifecycleOwner){
            if(it == true) showProgressBar()
            else hideProgressBar()
        }
    }
    private fun showProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
    }
    private fun hideProgressBar(){
        binding.progressBar.visibility = View.INVISIBLE
    }
    private fun showError(exception: Exception){
        Toast.makeText(requireContext(), "Ошибка: ${exception.message}", Toast.LENGTH_LONG).show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}