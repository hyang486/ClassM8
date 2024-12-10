package com.example.scheduleapp.ui.event

import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.scheduleapp.R
import com.example.scheduleapp.base.BaseFragment
import com.example.scheduleapp.base.ViewEvent
import com.example.scheduleapp.base.ViewState
import com.example.scheduleapp.databinding.FragmentGoogleMapBinding
import com.example.scheduleapp.ext.showToast
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch



@AndroidEntryPoint
class GoogleMapFragment :
    BaseFragment<FragmentGoogleMapBinding>(R.layout.fragment_google_map),
    OnMapReadyCallback, OnMapLongClickListener {

    private var googleMap: GoogleMap? = null

    override val viewModel by viewModels<GoogleMapViewModel>()
    private val parentViewModel by activityViewModels<EventParentViewModel>()
    private var saveLocation: Marker? = null


    override fun initUi() {
        val mapFragment = SupportMapFragment.newInstance()
        childFragmentManager.beginTransaction()
            .replace(binding.addMap.id, mapFragment)
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
        googleMap?.setOnMapLongClickListener(this)
        lifecycleScope.launch {
            val saveLatLng = parentViewModel.saveLatLngStateFlow.first()
            if (saveLatLng.latitude != 0.0 && saveLatLng.longitude != 0.0) {
                saveLocation = googleMap?.addMarker(
                    MarkerOptions()
                        .position(saveLatLng)
                        .title("Save Location")
                )
                googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(saveLatLng, 15f))
            }
        }
    }
    override fun onMapLongClick(latlng: LatLng) {
        saveLocation?.remove()
        saveLocation = googleMap?.addMarker(
            MarkerOptions()
                .position(latlng)
                .title("Save Location")
        )
        showToast(message = "Save Location!")
        parentViewModel.saveLatLng(latlng)
    }
}
