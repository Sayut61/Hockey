package com.sayut61.hockey.ui.calendar

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sayut61.hockey.databinding.FragmentCalendarBinding
import com.sayut61.hockey.domain.entities.GameFullInfo
import com.sayut61.hockey.domain.entities.GameGeneralInfo
import com.sayut61.hockey.ui.adapters.CalendarAdapter
import com.sayut61.hockey.ui.adapters.CalendarAdapterListener
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class CalendarFragment : Fragment(), CalendarAdapterListener {
    private val viewModel: CalendarViewModel by viewModels()
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.gameFullInfoLiveData.observe(viewLifecycleOwner) {
            showCalendarInfo(it)
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            showError(it)
        }
        binding.calendarView.setOnDateChangeListener { calendarView, year, month, day ->
            viewModel.changeDate(LocalDate.of(year, month+1, day))
        }
        viewModel.progressBarLiveData.observe(viewLifecycleOwner){
            if (it == true){ showProgressBar() }
            else hideProgressBar()
        }
    }

    private fun showError(ex: Exception) {
        Toast.makeText(requireContext(),"Ошибка - ${ex.message}", Toast.LENGTH_LONG).show()
    }

    private fun showCalendarInfo(gameFullInfo: List<GameFullInfo>) {
        val adapter = CalendarAdapter(gameFullInfo, this, activity)
        binding.recyclerViewCalendar.adapter = adapter
    }

    override fun onCalendarClick(gameFullInfo: GameFullInfo) {
        val action = CalendarFragmentDirections.actionCalendarFragmentToCalendarDetailFragment(gameFullInfo.generalInfo)
        findNavController().navigate(action)
    }
    private fun showProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
    }
    private fun hideProgressBar(){
        binding.progressBar.visibility = View.INVISIBLE
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onFavButtonClick(gameFullInfo: GameFullInfo) {
        if (gameFullInfo.generalInfo.isInFavoriteGame)
            viewModel.removeGameInDB(gameFullInfo.generalInfo)
        else
            viewModel.addGameInDB(gameFullInfo.generalInfo)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}