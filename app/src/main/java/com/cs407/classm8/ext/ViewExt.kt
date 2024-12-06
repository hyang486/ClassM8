package com.example.classm8.ext

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.classm8.ui.splash.SplashFragmentDirections

fun Fragment.routeLoginFragment() {
    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
}

fun Fragment.routeHomeFragment() {
    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
}



fun Fragment.showToast(context: Context = this.requireContext(), message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

