package com.watermelon.kanbanboard.ui.todo

import android.util.Log
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
import com.watermelon.kanbanboard.ui.interfaces.UpdateTabLayout
import com.watermelon.kanbanboard.util.Constant

class TodoFragment(private val listener: CustomDialogFragment,
                   private val updateTabLayoutListener: UpdateTabLayout
                   ) : BaseFragment<FragmentTodoBinding>(), UpdateAdapter {
    override val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentTodoBinding
        get() = FragmentTodoBinding::inflate

    lateinit var adapter: TaskAdapter

    override fun setup() {}

    override fun callBack() {
        updateRecycler()
        binding.addButton.setOnClickListener { listener.showDialog(AddFragment(listener, this, Constant.FragmentType.ADD, null)) }
    }

    override fun onResume() {
        super.onResume()
        adapter.setData(DataManager.todoList)
    }

    override fun update() {
        Log.v("TEST_UPDATE", "UPDATE TODO")
        adapter.setData(DataManager.todoList)
        updateTabLayoutListener.update()
    }

    override fun editTask(task: Task) {
        listener.showDialog(AddFragment(listener, this, Constant.FragmentType.EDIT, task))
    }

    private fun updateRecycler() {
        adapter = TaskAdapter(DataManager.todoList,this, updateTabLayoutListener)
        binding.todoRecycler.adapter = adapter
    }
}