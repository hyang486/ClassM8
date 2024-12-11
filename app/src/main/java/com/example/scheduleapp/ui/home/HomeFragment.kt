package com.example.scheduleapp.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.scheduleapp.R
import com.example.scheduleapp.base.BaseFragment
import com.example.scheduleapp.base.ViewEvent
import com.example.scheduleapp.base.ViewState
import com.example.scheduleapp.databinding.FragmentHomeBinding
import com.example.scheduleapp.ui.adapter.CurrentScheduleAdapter
import com.example.scheduleapp.ui.adapter.UpcomingEventsAdapter
import com.example.scheduleapp.ui.event.add.AddEventActivity
import com.example.scheduleapp.ui.event.update.UpdateEventActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    override val viewModel by viewModels<HomeViewModel>()

    private val upcomingEventsAdapter = UpcomingEventsAdapter()
    private val currentScheduleAdapter = CurrentScheduleAdapter { clickItem ->
        startActivity(UpdateEventActivity.createIntent(context = requireContext(), event = clickItem))
    }
    override fun initUi() {
        binding.rvCurrentEvents.adapter = currentScheduleAdapter
        binding.rvUpcomingEvents.adapter = upcomingEventsAdapter
    }

    override fun onChangedViewState(state: ViewState) {
        when(state){
            is HomeViewState -> {
                currentScheduleAdapter.submitList(state.currentEvents)
                upcomingEventsAdapter.submitList(state.upcomingEvents)
                binding.rvCurrentEvents.isVisible = state.currentEvents.isNotEmpty()
                binding.emptyCurrentEventsTitle.isVisible = state.currentEvents.isEmpty()

                binding.rvUpcomingEvents.isVisible = state.upcomingEvents.isNotEmpty()
                binding.emptyUpcommingEventsTitle.isVisible = state.upcomingEvents.isEmpty()
            }
        }
    }

    override fun onChangeViewEvent(event: ViewEvent) {
        super.onChangeViewEvent(event)
        when (event) {
            is HomeViewEvent.Logout -> {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
            }

            is HomeViewEvent.AddEvent -> {
                startActivity<AddEventActivity>()
            }

            is HomeViewEvent.OpenDrawer -> {
                binding.dlMain.openDrawer(binding.drawer)
            }

            is HomeViewEvent.CloseDrawer -> {
                binding.dlMain.closeDrawer(binding.drawer)
            }
        }
    }
    private inline fun <reified T : AppCompatActivity> startActivity() {
        startActivity(Intent(requireActivity(), T::class.java))
    }
}

