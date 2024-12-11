package com.example.scheduleapp.ui.event.update

import android.graphics.Color
import android.graphics.Paint
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.scheduleapp.R
import com.example.scheduleapp.base.BaseFragment
import com.example.scheduleapp.base.ViewEvent
import com.example.scheduleapp.base.ViewState
import com.example.scheduleapp.databinding.FragmentUpdateEventBinding
import com.example.scheduleapp.ui.event.EventParentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

const val ARG_SELECT_ITEM = "item_select_detection"

@AndroidEntryPoint
class UpdateEventFragment :
    BaseFragment<FragmentUpdateEventBinding>(R.layout.fragment_update_event) {

    override val viewModel by viewModels<UpdateEventViewModel>()

    private val parentViewModel by activityViewModels<EventParentViewModel>()

    override fun initUi() {
        parentViewModel.saveLatLngStateFlow
            .onEach {
                viewModel.updateLocation(it)
                val (text, color) = if (it.latitude != 0.0 && it.longitude != 0.0) {
                    "Show Save Location" to Color.BLUE
                } else {
                    "Click to Position the map" to Color.BLACK
                }
                binding.tvShowMap.paintFlags = Paint.UNDERLINE_TEXT_FLAG
                binding.tvShowMap.text = text
                binding.tvShowMap.setTextColor(color)
            }
            .onEach(viewModel::updateLocation)
            .launchIn(lifecycleScope)
    }

    override fun onChangedViewState(state: ViewState) {}

    override fun onChangeViewEvent(event: ViewEvent) {
        super.onChangeViewEvent(event)
        when (event) {
            is UpdateEventViewEvent.BackPress -> {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }

            is UpdateEventViewEvent.RouteGoogleMap -> {
                findNavController().navigate(UpdateEventFragmentDirections.actionUpdateEventFragmentToGoogleMapFragment())
            }
        }
    }
}