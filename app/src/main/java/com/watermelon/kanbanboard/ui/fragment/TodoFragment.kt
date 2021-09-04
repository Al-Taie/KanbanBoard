package com.watermelon.kanbanboard.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.watermelon.kanbanboard.databinding.FragmentTodoBinding
import com.watermelon.kanbanboard.ui.base.BaseFragment

class TodoFragment : BaseFragment<FragmentTodoBinding>() {


    override val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentTodoBinding = FragmentTodoBinding::inflate
    override fun setup() {}

    override fun callBack() {}
}