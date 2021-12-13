package com.sayut61.hockey.ui.calendar

import android.graphics.drawable.ClipDrawable.HORIZONTAL
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sayut61.hockey.R
import com.sayut61.hockey.databinding.FragmentCalendarBinding
import com.sayut61.hockey.domain.entities.GameFullInfo
import com.sayut61.hockey.ui.adapters.*
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class CalendarFragment : Fragment(), CalendarAdapterListener, CalendarDateAdapterListener {
    private val viewModel: CalendarViewModel by viewModels()
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    lateinit var myCalendar: MyCalendar
    lateinit var adapter: CalendarAdapter
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
        myCalendar = MyCalendar(requireContext())
        adapter = CalendarAdapter(this, activity)
        setSelectedDayTitle(myCalendar.getCalendarDay())
        binding.recyclerViewCalendar.adapter = adapter
        binding.recyclerViewCalendar.itemAnimator = null
        viewModel.gamesLiveData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                adapter.submitList(it)
                binding.recyclerViewCalendar.visibility = View.VISIBLE
                binding.emptyListTextView.visibility = View.GONE
            }else {
                binding.emptyListTextView.visibility = View.VISIBLE
                binding.recyclerViewCalendar.visibility = View.INVISIBLE
            }
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            showError(it)
        }
        binding.daysOfMonthRecyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false)
        viewModel.changeDate(myCalendar.getCurrentDay())
        updateCalendarView()
        binding.nextImageButton.setOnClickListener {
            myCalendar.addMonth()
            updateCalendarView()
        }
        binding.beforeImageButton.setOnClickListener {
            myCalendar.reduceMonth()
            updateCalendarView()
        }
        viewModel.progressBarLiveData.observe(viewLifecycleOwner){
            if (it == true){ showProgressBar() }
            else hideProgressBar()
        }
    }

    //-----------------------------------------------------------------
    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateCalendarView() {
        val listDays = myCalendar.getDaysList()
        val adapter = CalendarDateAdapter(listDays, myCalendar.day, this)
        binding.daysOfMonthRecyclerView.adapter = adapter
        binding.daysOfMonthRecyclerView.scrollToPosition(myCalendar.day-1)
        binding.monthTextView.text = myCalendar.getCurrentMonthName()
        binding.yearTextView.text = myCalendar.year.toString()
    }
    override fun onCalendarClick(gameFullInfo: GameFullInfo) {
        val action = CalendarFragmentDirections.actionCalendarFragmentToCalendarDetailFragment(gameFullInfo.generalInfo)
        findNavController().navigate(action)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onFavButtonClick(gameFullInfo: GameFullInfo) {
        viewModel.onFavoriteClick(gameFullInfo.generalInfo)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDayClick(day: CalendarDay) {
        viewModel.changeDate(LocalDate.of(day.year, day.month, day.number))
        setSelectedDayTitle(day)
    }

    private fun setSelectedDayTitle(day: CalendarDay) {
        binding.currentDayTextView.text = day.number.toString()
        binding.currentMounthTextView.text = day.month.toString()
    }

    //----------------------------------------------------------------------------
    private fun showError(ex: Exception) {
        Toast.makeText(requireContext(),"Ошибка - ${ex.message}", Toast.LENGTH_LONG).show()
    }
    private fun showProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
    }
    private fun hideProgressBar(){
        binding.progressBar.visibility = View.INVISIBLE
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}