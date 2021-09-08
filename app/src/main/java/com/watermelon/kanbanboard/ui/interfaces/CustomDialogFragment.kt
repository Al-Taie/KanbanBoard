package com.watermelon.kanbanboard.ui.interfaces

import android.view.View
import androidx.fragment.app.DialogFragment

interface CustomDialogFragment {
    fun showDialog(fragment: DialogFragment)
    fun showDialog(view: View)
    fun closeDialog(dialogFragment: DialogFragment)
    fun showDatePicker()
}