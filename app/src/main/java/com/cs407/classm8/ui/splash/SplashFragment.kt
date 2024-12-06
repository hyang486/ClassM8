package com.example.classm8.ui.splash

import android.util.Log
import android.view.animation.AnimationUtils
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.example.classm8.R
import com.example.classm8.base.BaseFragment
import com.example.classm8.base.ViewEvent
import com.example.classm8.base.ViewState
import com.example.classm8.databinding.FragmentSplashBinding
import com.example.classm8.ext.routeHomeFragment
import com.example.classm8.ext.routeLoginFragment
import com.example.classm8.ext.showToast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

    override val viewModel by viewModels<SplashViewModel>()

    override fun initUi() {
        binding.load.animation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)
        permissionResultLauncher.launch(
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    private val permissionResultLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted ->
        if (isGranted) {
            viewModel.load()
        } else {
            showToast(message = "Please allow location permission.")
            requireActivity().finish()
        }
    }

    override fun onChangedViewState(state: ViewState) {
        when (state) {
            is SplashViewState.RouteLogin -> routeLoginFragment()
            is SplashViewState.RouteHome -> routeHomeFragment()
        }
    }
}