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
import com.sayut61.hockey.databinding.FragmentHomeTeamPlayersBinding
import com.sayut61.hockey.domain.entities.GameGeneralInfo
import com.sayut61.hockey.ui.calendar.calendardetail.CalendarDetailFragmentArgs
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class HomeTeamRecyclerFragment : Fragment(){
    private val viewModel: HomeTeamRecyclerViewModel by viewModels()
    private var _binding: FragmentHomeTeamPlayersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeTeamPlayersBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gameGeneralInfo = arguments?.getParcelable<GameGeneralInfo>(GAME_KEY)!!
        viewModel.refreshFragment(gameGeneralInfo)
        viewModel.homePlayersLiveData.observe(viewLifecycleOwner){
            val adapter = HomeTeamAdapter(it)
            binding.homeTeamRecyclerView.adapter = adapter
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

    companion object{
        private const val GAME_KEY = "game"
        fun getInstance(game: GameGeneralInfo): HomeTeamRecyclerFragment{
            return HomeTeamRecyclerFragment().apply {
                arguments = Bundle().apply{
                    putParcelable(GAME_KEY,game)
                }
            }
        }
    }
}