package com.sayut61.hockey.ui.players.playerinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.sayut61.hockey.databinding.FragmentPlayerInfoBinding
import com.sayut61.hockey.domain.entities.PlayerFullInfo
import com.sayut61.hockey.ui.utils.loadImage
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class PlayerDetailInfoFragment : Fragment() {
    private val viewModel: PlayerDetailInfoViewModel by viewModels()
    private var _binding: FragmentPlayerInfoBinding? = null
    private val binding get() = _binding!!
    private val args: PlayerDetailInfoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPlayerInfoBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.refreshPlayerDetail(args.playerId)

        viewModel.playerLiveData.observe(viewLifecycleOwner){
            showPlayerFullInfo(it)
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner){
            showError(it)
        }
        viewModel.progressBarLiveData.observe(viewLifecycleOwner){
            if(it == true)showProgressBar()
            else hideProgressBar()
        }
    }

    private fun showPlayerFullInfo(playerFullInfo: PlayerFullInfo) {
        binding.birthCityTextView.text = playerFullInfo.birthCity
        binding.nationalityTextView.text = playerFullInfo.nationality
        binding.playerBirthdayTextView.text = playerFullInfo.birthDate
        binding.playerFullNameTextView.text = playerFullInfo.fullName
        binding.playerNumberTextView.text = playerFullInfo.playerNumber
        binding.positionTextView.text = playerFullInfo.position
        binding.teamNameTextView.text = playerFullInfo.teamFullName
        binding.wingTextView.text = playerFullInfo.wing
        playerFullInfo.teamLogo.let { logoUrl ->
            if (logoUrl != null) {
                loadImage(logoUrl, activity, binding.teamLogoImageView)
            }
        }
    }
    private fun showError(ex: Exception){
        Toast.makeText(requireContext(),"Ошибка - ${ex.message}", Toast.LENGTH_LONG ).show()
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