package com.example.core

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

object Toast {

    private var toast: Toast? = null

    fun Fragment.showToast(@StringRes text: Int, toastLength: ToastLength = ToastLength.Short) {
        toast?.cancel()
        toast = Toast.makeText(context, text, toastLength.value)
        toast?.show()
    }

    enum class ToastLength(val value: Int) {
        Short(Toast.LENGTH_SHORT),
        Long(Toast.LENGTH_LONG)
    }
}