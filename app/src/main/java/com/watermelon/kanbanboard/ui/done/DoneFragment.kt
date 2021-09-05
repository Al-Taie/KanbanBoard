package com.watermelon.kanbanboard.ui.done

import android.view.LayoutInflater
import android.view.ViewGroup
import com.watermelon.kanbanboard.databinding.FragmentDoneBinding
import com.watermelon.kanbanboard.ui.base.BaseFragment

class DoneFragment : BaseFragment<FragmentDoneBinding>() {

    override val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentDoneBinding
        get() = FragmentDoneBinding::inflate

    override fun setup() {}

    override fun callBack() {}


}