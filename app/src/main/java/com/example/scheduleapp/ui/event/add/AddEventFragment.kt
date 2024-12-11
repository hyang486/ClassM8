package com.example.scheduleapp.ui.event.add

import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.scheduleapp.R
import com.example.scheduleapp.base.BaseFragment
import com.example.scheduleapp.base.ViewEvent
import com.example.scheduleapp.base.ViewState
import com.example.scheduleapp.databinding.FragmentAddEventBinding
import com.example.scheduleapp.ext.showToast
import com.example.scheduleapp.ui.event.EventParentViewModel
import com.example.scheduleapp.ui.event.GoogleMapViewEvent
import com.example.scheduleapp.ui.event.GoogleMapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddEventFragment : BaseFragment<FragmentAddEventBinding>(R.layout.fragment_add_event),
    OnMapReadyCallback , OnMapLongClickListener {

    private var googleMap: GoogleMap? = null

    override val viewModel by viewModels<AddEventViewModel>()

    private val parentViewModel by activityViewModels<EventParentViewModel>()

    private val googleMapViewModel by viewModels<GoogleMapViewModel>()

    private var saveLocation: Marker? = null

    override fun initUi() {
        parentViewModel.saveLatLngStateFlow.onEach(viewModel::updateLocation).launchIn(lifecycleScope)
        googleMapViewModel.viewState.map(::onChangedViewState).launchIn(viewLifecycleOwner.lifecycleScope)
        googleMapViewModel.viewEvent.map(::onChangeViewEvent).launchIn(viewLifecycleOwner.lifecycleScope)


        binding.googleMapViewModel = googleMapViewModel

        val mapFragment = SupportMapFragment.newInstance()
        childFragmentManager.beginTransaction()
            .replace(binding.addMap.id, mapFragment)
            .commit()
        mapFragment.getMapAsync(this)
    }

    override fun onChangedViewState(state: ViewState) {}

    override fun onChangeViewEvent(event: ViewEvent) {
        super.onChangeViewEvent(event)
        when (event) {
            is AddEventViewEvent.BackPress -> {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }

            is AddEventViewEvent.RouteGoogleMap -> {
                findNavController().navigate(AddEventFragmentDirections.actionAddEventFragmentToGoogleMapFragment())
            }
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
        googleMap?.setOnMapClickListener {
            binding.scrollView.requestDisallowInterceptTouchEvent(true)
        }

        googleMap?.setOnCameraMoveStartedListener {
            binding.scrollView.requestDisallowInterceptTouchEvent(true)
        }

        googleMap?.setOnCameraIdleListener {
            binding.scrollView.requestDisallowInterceptTouchEvent(false)
        }

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
