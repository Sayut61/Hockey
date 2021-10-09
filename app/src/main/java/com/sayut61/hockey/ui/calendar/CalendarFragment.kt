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
import com.sayut61.hockey.domain.entities.Game
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

        viewModel.gamesLiveData.observe(viewLifecycleOwner) {
            showCalendarInfo(it)
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            showError(it)
        }
        binding.calendarView.setOnDateChangeListener { calendarView, year, month, day ->
            viewModel.changeDate(LocalDate.of(year, month, day))
        }
    }

    private fun showError(ex: Exception) {
        Toast.makeText(requireContext(),"Ошибка - ${ex.message}", Toast.LENGTH_LONG).show()
    }

    private fun showCalendarInfo(game: List<Game>) {
        val adapter = CalendarAdapter(game, this)
        binding.recyclerViewCalendar.adapter = adapter
    }

    override fun onCalendarClick(game: Game) {
        val action = CalendarFragmentDirections.actionCalendarFragmentToCalendarDetailFragment(game)
        findNavController().navigate(action)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onFavButtonClick(game: Game) {
        if (game.isInFavoriteGame)
            viewModel.removeGameInDB(game)
        else
            viewModel.addGameInDB(game)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}