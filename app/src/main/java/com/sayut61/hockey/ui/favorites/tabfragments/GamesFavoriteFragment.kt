package com.sayut61.hockey.ui.favorites.tabfragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sayut61.hockey.databinding.FragmentGameFavoriteBinding
import com.sayut61.hockey.domain.entities.GameGeneralInfo
import com.sayut61.hockey.ui.adapters.GameFavoriteAdapter
import com.sayut61.hockey.ui.adapters.GameFavoriteAdapterListener
import com.sayut61.hockey.ui.favorites.ViewPagerFavoriteFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
@AndroidEntryPoint
class GamesFavoriteFragment() : Fragment(), GameFavoriteAdapterListener{
    private val viewModel: GamesFavoriteViewModel by viewModels()
    private var _binding: FragmentGameFavoriteBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: GameFavoriteAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentGameFavoriteBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = GameFavoriteAdapter(this, activity as? Activity)
        binding.favoriteGameRecyclerView.adapter = adapter
        viewModel.gamesFavoriteLiveData.observe(viewLifecycleOwner){
            if (it.isNotEmpty()) {
                adapter.submitList(it)
                binding.emptyListTextView.visibility = View.GONE
                binding.favoriteGameRecyclerView.visibility = View.VISIBLE
            }else{
                binding.emptyListTextView.visibility = View.VISIBLE
                binding.favoriteGameRecyclerView.visibility = View.INVISIBLE
            }
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner){
            showError(it)
        }
        viewModel.progressBarLiveData.observe(viewLifecycleOwner){
            if (it == true){ showProgressBar() }
            else hideProgressBar()
        }
        viewModel.refreshFavoriteFragment()
    }
    override fun onGameClick(gameGeneralInfo: GameGeneralInfo) {
        val action = ViewPagerFavoriteFragmentDirections.actionFavoriteFragmentToCalendarDetailFragment(gameGeneralInfo)
        findNavController().navigate(action)
    }
    private fun showProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
    }
    private fun hideProgressBar(){
        binding.progressBar.visibility = View.INVISIBLE
    }
    override fun onDeleteButtonClick(gameGeneralInfo: GameGeneralInfo) {
        viewModel.deleteFromFavorite(gameGeneralInfo)
    }
    private fun showError(ex: Exception){
        Toast.makeText(requireContext(),"Ошибка - ${ex.message}", Toast.LENGTH_LONG).show()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}