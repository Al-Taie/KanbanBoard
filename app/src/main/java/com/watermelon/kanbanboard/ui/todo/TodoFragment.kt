package com.watermelon.kanbanboard.ui.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import com.watermelon.kanbanboard.data.DataManager
import com.watermelon.kanbanboard.data.domain.Task
import com.watermelon.kanbanboard.databinding.FragmentTodoBinding
import com.watermelon.kanbanboard.ui.adapter.TaskAdapter
import com.watermelon.kanbanboard.ui.add.AddFragment
import com.watermelon.kanbanboard.ui.base.BaseFragment
import com.watermelon.kanbanboard.ui.interfaces.CustomDialogFragment
import com.watermelon.kanbanboard.ui.interfaces.UpdateAdapter

class TodoFragment(private val listener: CustomDialogFragment) : BaseFragment<FragmentTodoBinding>(), UpdateAdapter {
    override val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentTodoBinding
        get() = FragmentTodoBinding::inflate

    override fun setup() {}

    override fun callBack() {
        binding.todoRecycler.adapter = TaskAdapter(DataManager.todoList)
        binding.addButton.setOnClickListener { listener.showDialog(AddFragment(listener, this)) }
    }

    override fun update() {
        binding.todoRecycler.adapter = TaskAdapter(DataManager.todoList)
    }
}