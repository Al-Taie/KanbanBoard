package com.watermelon.kanbanboard.ui.interfaces

import androidx.fragment.app.DialogFragment

interface CustomDialogFragment {
    fun showDialog(fragment: DialogFragment)
    fun closeDialog(dialogFragment: DialogFragment)
    fun showDatePicker()
}