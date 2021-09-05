package com.watermelon.kanbanboard.ui.add

import android.view.LayoutInflater
import android.view.ViewGroup
import com.watermelon.kanbanboard.databinding.FragmentAddBinding
import com.watermelon.kanbanboard.databinding.FragmentDoneBinding
import com.watermelon.kanbanboard.ui.base.BaseFragment

class AddFragment : BaseFragment<FragmentAddBinding>() {

    override val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentAddBinding
        get() = FragmentAddBinding::inflate

    override fun setup() {}

    override fun callBack() {}


}