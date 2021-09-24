package com.sayut61.hockey.ui.calendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sayut61.hockey.databinding.FragmentCalendarBinding
import com.sayut61.hockey.domain.entities.Calendar
import com.sayut61.hockey.ui.adapters.CalendarAdapter
import com.sayut61.hockey.ui.adapters.CalendarAdapterListener
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import java.lang.reflect.GenericArrayType

@AndroidEntryPoint
class CalendarFragment : Fragment(), CalendarAdapterListener {
    private val calendarViewModel: CalendarViewModel by viewModels()
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calendarViewModel.refreshViewModel()
        calendarViewModel.calendarLiveData.observe(viewLifecycleOwner){
            showCalendarInfo(it)
        }
        calendarViewModel.errorLiveData.observe(viewLifecycleOwner){
            showError(it)
        }
    }
    private fun showError(ex: Exception){
        Toast.makeText(requireContext(), "Ошибка - ${ex.message}", Toast.LENGTH_LONG).show()
    }
    private fun showCalendarInfo(game: List<Calendar>){
        val adapter = CalendarAdapter(game, this)
        binding.recyclerViewCalendar.adapter = adapter
    }
    override fun onCalendarClick(game: Calendar) {
        val action = CalendarFragmentDirections.actionCalendarFragmentToCalendarDetailFragment(game)
        findNavController().navigate(action)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}