package com.watermelon.kanbanboard.ui.in_progress

import android.view.LayoutInflater
import android.view.ViewGroup
import com.watermelon.kanbanboard.data.DataManager
import com.watermelon.kanbanboard.data.domain.Task
import com.watermelon.kanbanboard.databinding.FragmentInProgressBinding
import com.watermelon.kanbanboard.ui.adapter.TaskAdapter
import com.watermelon.kanbanboard.ui.add.AddFragment
import com.watermelon.kanbanboard.ui.base.BaseFragment
import com.watermelon.kanbanboard.ui.interfaces.CustomDialogFragment
import com.watermelon.kanbanboard.ui.interfaces.UpdateAdapter
import com.watermelon.kanbanboard.ui.interfaces.UpdateTabLayout
import com.watermelon.kanbanboard.util.Constant

class InProgressFragment(
    private val listener: CustomDialogFragment,
    private val updateTabLayoutListener: UpdateTabLayout
) : BaseFragment<FragmentInProgressBinding>(), UpdateAdapter {
    override val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentInProgressBinding
        get() = FragmentInProgressBinding::inflate

    private lateinit var adapter: TaskAdapter

    override fun setup() {
        updateRecycler()
    }

    override fun callBack() {}

    override fun onResume() {
        super.onResume()
        adapter.setData(DataManager.inProgressList)
    }

    override fun update() {
        adapter.setData(DataManager.inProgressList)
        updateTabLayoutListener.update()
    }

    override fun editTask(task: Task) {
        listener.showDialog(AddFragment(listener, this, Constant.FragmentType.EDIT, task))
    }

    private fun updateRecycler() {
        adapter = TaskAdapter(DataManager.inProgressList, this, updateTabLayoutListener)
        binding.inProgressRecycler.adapter = adapter
    }
}