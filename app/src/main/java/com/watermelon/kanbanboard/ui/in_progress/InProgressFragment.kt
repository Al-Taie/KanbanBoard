package com.watermelon.kanbanboard.ui.in_progress

import android.view.LayoutInflater
import android.view.ViewGroup
import com.watermelon.kanbanboard.databinding.FragmentInProgressBinding
import com.watermelon.kanbanboard.ui.base.BaseFragment

class InProgressFragment : BaseFragment<FragmentInProgressBinding>() {
    override val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentInProgressBinding
        get() = FragmentInProgressBinding::inflate

    override fun setup() {

    }

    override fun callBack() {

    }


}