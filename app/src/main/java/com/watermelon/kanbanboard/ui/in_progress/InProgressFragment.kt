package com.watermelon.kanbanboard.ui.in_progress

import android.view.LayoutInflater
import android.view.ViewGroup
import com.watermelon.kanbanboard.data.DataManager
import com.watermelon.kanbanboard.data.domain.Task
import com.watermelon.kanbanboard.databinding.FragmentInProgressBinding
import com.watermelon.kanbanboard.ui.adapter.TaskAdapter
import com.watermelon.kanbanboard.ui.base.BaseFragment
import com.watermelon.kanbanboard.ui.interfaces.UpdateAdapter

class InProgressFragment : BaseFragment<FragmentInProgressBinding>(), UpdateAdapter {
    override val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentInProgressBinding
        get() = FragmentInProgressBinding::inflate

    override fun setup() {
        binding.inProgressRecycler.adapter = TaskAdapter(DataManager.inProgressList, this)
    }

    override fun callBack() {

    }

    override fun update() {
        TODO("Not yet implemented")
    }

    override fun editTask(task: Task) {
        TODO("Not yet implemented")
    }


}