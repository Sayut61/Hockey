package com.sayut61.hockey.ui.calendar.calendardetail.adapters_and_recyclerFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.sayut61.hockey.R
import com.sayut61.hockey.databinding.FragmentAwayTeamPlayersBinding
import com.sayut61.hockey.domain.entities.GameGeneralInfo
import com.sayut61.hockey.domain.entities.PlayerNameAndNumber
import com.sayut61.hockey.ui.calendar.calendardetail.CalendarDetailFragmentArgs
import com.sayut61.hockey.ui.calendar.calendardetail.CalendarDetailFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class AwayTeamRecyclerFragment: Fragment(), AwayTeamAdapterListener {
    private val viewModel: AwayTeamRecyclerViewModel by viewModels()
    private var _binding: FragmentAwayTeamPlayersBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAwayTeamPlayersBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gameGeneralInfo = arguments?.getParcelable<GameGeneralInfo>(GAME_KEY)!!
        viewModel.refreshFragment(gameGeneralInfo)
        viewModel.awayPlayersLiveData.observe(viewLifecycleOwner){
            val adapter = AwayTeamAdapter(it, this)
            binding.awayTeamRecyclerView.adapter = adapter
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner){
            showError(it)
        }
        viewModel.progressBarLiveData.observe(viewLifecycleOwner){
            if(it == true) showProgressBar()
            else hideProgressBar()
        }
        val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_drawable))
        binding.awayTeamRecyclerView.addItemDecoration(dividerItemDecoration)
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
    companion object{
        private const val GAME_KEY = "game"
        fun getInstance(game: GameGeneralInfo): AwayTeamRecyclerFragment{
            return AwayTeamRecyclerFragment().apply {
                arguments = Bundle().apply{
                    putParcelable(GAME_KEY,game)
                }
            }
        }
    }
    override fun onPlayerClick(player: PlayerNameAndNumber) {
        val action = CalendarDetailFragmentDirections.actionCalendarDetailFragmentToPlayerDetailInfoFragment(player.id)
        findNavController().navigate(action)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}