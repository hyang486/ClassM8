package com.example.classm8.ext

import android.animation.Animator
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView

@BindingAdapter("onLottieAnimateState")
fun LottieAnimationView.onLottieAnimateState(state : Function1<LottieAnimateState,Unit>?){
    this.addAnimatorListener(object :Animator.AnimatorListener{
        override fun onAnimationStart(animation: Animator) {
            state?.invoke(LottieAnimateState.Start)
        }

        override fun onAnimationEnd(animation: Animator) {
            state?.invoke(LottieAnimateState.End)
        }

        override fun onAnimationCancel(animation: Animator) {
            state?.invoke(LottieAnimateState.Cancel)
        }

        override fun onAnimationRepeat(animation: Animator) {
            state?.invoke(LottieAnimateState.Repeat)
        }

    })
}

sealed interface LottieAnimateState {
    data object Start : LottieAnimateState
    data object End : LottieAnimateState
    data object Cancel : LottieAnimateState
    data object Repeat : LottieAnimateState
}

@BindingAdapter("onEditorAction")
fun EditText.setOnEditorActionListener(listener : () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            listener()
            true
        } else {
            false
        }
    }
}