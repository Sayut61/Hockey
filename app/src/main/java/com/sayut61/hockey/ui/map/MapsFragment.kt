package com.sayut61.hockey.ui.map

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.sayut61.hockey.R
import com.sayut61.hockey.databinding.FragmentCalendarBinding
import com.sayut61.hockey.databinding.FragmentMapsBinding
import com.sayut61.hockey.domain.entities.Stadium
import com.sayut61.hockey.domain.usecases.MapUseCases
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import javax.inject.Inject
@AndroidEntryPoint
class MapsFragment : Fragment() {
    private val viewModel: MapsViewModel by viewModels()
    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private val callback = OnMapReadyCallback { googleMap ->
        val centerUSA = LatLng(39.7, -97.3)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerUSA, 3.2f))
        viewModel.mapLiveData.observe(viewLifecycleOwner) { stadiums->
            binding.mapView.visibility = View.VISIBLE
            binding.exceptionTextView.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.INVISIBLE
            addMarker(googleMap, stadiums)
            binding.mapView.onResume()
        }
    }

    private fun addMarker(googleMap: GoogleMap, markers: List<Stadium>) {
        markers.forEach {
            googleMap.addMarker(
                MarkerOptions()
                    .position(LatLng(it.geoLat, it.geoLong))
                    .title(it.nameStadium)
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.refreshListStadium()
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(callback)
        viewModel.exceptionLiveData.observe(viewLifecycleOwner){
            binding.mapView.visibility = View.INVISIBLE
            binding.exceptionTextView.visibility = View.VISIBLE
            binding.progressBar.visibility = View.INVISIBLE
            binding.exceptionTextView.text = it.message
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}