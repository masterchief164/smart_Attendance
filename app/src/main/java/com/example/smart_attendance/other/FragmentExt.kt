package com.example.smart_attendance.other

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun snackbar(text: String, layout: View){

    Snackbar.make(
        layout,
        text,
        Snackbar.LENGTH_LONG
    ).show()
}