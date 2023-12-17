package com.example.weatherapp.common.ext

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

fun Fragment.onBackPressed(block: () -> Unit) {
    val callback: OnBackPressedCallback =
        object : OnBackPressedCallback(isResumed) {
            override fun handleOnBackPressed() {
                block()
            }
        }
    requireActivity().onBackPressedDispatcher.addCallback(this, callback)
}