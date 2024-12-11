package com.example.scheduleapp.ui.map

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.scheduleapp.R
import com.example.scheduleapp.base.BaseFragment
import com.example.scheduleapp.base.ViewEvent
import com.example.scheduleapp.base.ViewState
import com.example.scheduleapp.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GoogleMapFragment : BaseFragment<FragmentMapBinding>(R.layout.fragment_map),
    OnMapReadyCallback {

    private var googleMap: GoogleMap? = null

    override val viewModel by viewModels<GoogleMapViewModel>()

    override fun initUi() {
        val mapFragment = SupportMapFragment.newInstance()
        childFragmentManager.beginTransaction()
            .replace(binding.map.id, mapFragment)
            .commit()
        mapFragment.getMapAsync(this)
    }

    override fun onChangedViewState(state: ViewState) {

    }

    override fun onChangeViewEvent(event: ViewEvent) {
        super.onChangeViewEvent(event)
        when (event) {
            is GoogleMapViewEvent.MoveCurrentLocation -> {
                googleMap?.addMarker(
                    MarkerOptions()
                        .position(event.latLng)
                        .title("Current Location")
                )
                googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(event.latLng, 15f))
            }

            is GoogleMapViewEvent.ZoomIn -> {
                googleMap?.animateCamera(CameraUpdateFactory.zoomIn())
            }

            is GoogleMapViewEvent.ZoomOut -> {
                googleMap?.animateCamera(CameraUpdateFactory.zoomOut())
            }

            is GoogleMapViewEvent.Loading -> {
                binding.progress.isVisible = event.isVisible
                binding.progress.bringToFront()
            }
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
    }
}