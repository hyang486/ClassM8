package com.example.classm8.ui.event

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.classm8.R
import com.example.classm8.base.BaseFragment
import com.example.classm8.base.ViewEvent
import com.example.classm8.base.ViewState
import com.example.classm8.databinding.FragmentAddEventBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEventFragment : BaseFragment<FragmentAddEventBinding>(R.layout.fragment_add_event) {

    override val viewModel by viewModels<AddEventViewModel>()

    override fun initUi() {}

    override fun onChangedViewState(state: ViewState) {

    }

    override fun onChangeViewEvent(event: ViewEvent) {
        super.onChangeViewEvent(event)
        when (event) {
            is AddEventViewEvent.BackPress -> {
                findNavController().popBackStack()
            }
        }
    }
}
