package com.example.scheduleapp.ui.home

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.scheduleapp.R
import com.example.scheduleapp.base.BaseFragment
import com.example.scheduleapp.base.ViewEvent
import com.example.scheduleapp.base.ViewState
import com.example.scheduleapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    override val viewModel by viewModels<HomeViewModel>()

    override fun initUi() {}

    override fun onChangedViewState(state: ViewState) {

    }

    override fun onChangeViewEvent(event: ViewEvent) {
        super.onChangeViewEvent(event)
        when (event) {
            is HomeViewEvent.Logout -> {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
            }

            is HomeViewEvent.AddEvent -> {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddEventFragment())
            }

            is HomeViewEvent.OpenDrawer -> {
                binding.dlMain.openDrawer(binding.drawer)
            }

            is HomeViewEvent.CloseDrawer -> {
                binding.dlMain.closeDrawer(binding.drawer)
            }
        }
    }
}

