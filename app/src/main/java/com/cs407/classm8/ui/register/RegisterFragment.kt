package com.example.scheduleapp.ui.register

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.scheduleapp.R
import com.example.scheduleapp.base.BaseFragment
import com.example.scheduleapp.base.ViewEvent
import com.example.scheduleapp.base.ViewState
import com.example.scheduleapp.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(R.layout.fragment_register) {

    override val viewModel by viewModels<RegisterViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    override fun initUi() {}

    override fun onChangedViewState(state: ViewState) {
        when (state) {
            is RegisterViewState -> {
                binding.progressbar.bringToFront()
                binding.progressbar.isVisible = state.isLoading
                with(binding) {
                    inputEmailRegister.isEnabled = state.isEnable
                    inputPassRegisterOk.isEnabled = state.isEnable
                    inputPassRegister.isEnabled = state.isEnable
                }
            }
        }
    }

    override fun onChangeViewEvent(event: ViewEvent) {
        super.onChangeViewEvent(event)
        when (event) {
            is RegisterViewEvent.RouteHome -> {
                val action = RegisterFragmentDirections.actionRegisterFragmentToHomeFragment()
                findNavController().navigate(action)
            }
        }
    }
}