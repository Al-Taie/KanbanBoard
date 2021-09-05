package com.watermelon.kanbanboard.ui.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import com.watermelon.kanbanboard.databinding.FragmentTodoBinding
import com.watermelon.kanbanboard.ui.adapter.ItemAdapter
import com.watermelon.kanbanboard.ui.base.BaseFragment

class TodoFragment : BaseFragment<FragmentTodoBinding>() {
    override val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentTodoBinding
        get() = FragmentTodoBinding::inflate

    override fun setup() {}

    override fun callBack() {
//        val adapter = ItemAdapter(DataManager.Data)
//        binding.todoRecyclerview.adapter = adapter
    }
}