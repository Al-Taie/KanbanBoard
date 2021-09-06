package com.watermelon.kanbanboard.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.watermelon.kanbanboard.databinding.FragmentAddBinding
import com.watermelon.kanbanboard.ui.interfaces.CustomDialogFragment


class AddFragment(private val listener: CustomDialogFragment) : DialogFragment() {
    val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentAddBinding
        get() = FragmentAddBinding::inflate

    private lateinit var binding: FragmentAddBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = inflate(inflater, container, false)
        setup()
        callBack()
        return binding.root
    }

    private fun setup() {}

    private fun callBack() {
        binding.apply {
            dateView.setOnClickListener { listener.showDatePicker() }
            pickDateButton.setOnClickListener { listener.showDatePicker() }
        }
    }
}