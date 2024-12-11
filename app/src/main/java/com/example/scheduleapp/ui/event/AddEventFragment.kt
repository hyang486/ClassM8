package com.example.scheduleapp.ui.event

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.scheduleapp.R
import com.example.scheduleapp.base.BaseFragment
import com.example.scheduleapp.base.ViewEvent
import com.example.scheduleapp.base.ViewState
import com.example.scheduleapp.databinding.FragmentAddEventBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEventFragment : BaseFragment<FragmentAddEventBinding>(R.layout.fragment_add_event) {

    override val viewModel by viewModels<AddEventViewModel>()

    override fun initUi() {

    }

    override fun onChangedViewState(state: ViewState) {

    }

    override fun onChangeViewEvent(event: ViewEvent) {
        super.onChangeViewEvent(event)
        when (event) {
            is AddEventViewEvent.BackPress -> {
                findNavController().popBackStack()
            }

            is AddEventViewEvent.RouteGoogleMap -> {
                findNavController().navigate(AddEventFragmentDirections.actionAddEventFragmentToGoogleMapFragment())
            }
        }
    }
}
