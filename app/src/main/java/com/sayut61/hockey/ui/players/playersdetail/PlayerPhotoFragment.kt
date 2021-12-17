package com.sayut61.hockey.ui.players.playersdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.sayut61.hockey.databinding.FragmentPlayerPhotoBinding
import com.sayut61.hockey.ui.utils.loadImage

class PlayerPhotoFragment : DialogFragment(){
    private var _binding: FragmentPlayerPhotoBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPlayerPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val photoUrl = arguments?.getString("photoUrl")
        photoUrl?.let {
            loadImage(photoUrl, requireActivity(), binding.imageView)
        }
        binding.imageView.setOnClickListener {
            dismiss()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}