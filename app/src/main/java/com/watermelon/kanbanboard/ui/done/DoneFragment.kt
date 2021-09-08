package com.watermelon.kanbanboard.ui.done

import android.view.LayoutInflater
import android.view.ViewGroup
import com.watermelon.kanbanboard.data.DataManager
import com.watermelon.kanbanboard.data.domain.Task
import com.watermelon.kanbanboard.databinding.FragmentDoneBinding
import com.watermelon.kanbanboard.ui.adapter.TaskAdapter
import com.watermelon.kanbanboard.ui.base.BaseFragment
import com.watermelon.kanbanboard.ui.interfaces.UpdateAdapter

class DoneFragment : BaseFragment<FragmentDoneBinding>(), UpdateAdapter {

    override val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentDoneBinding
        get() = FragmentDoneBinding::inflate

    override fun setup() {
        binding.doneRecycler.adapter = TaskAdapter(DataManager.doneList, this)
    }

    override fun callBack() {}
    override fun update() {
        TODO("Not yet implemented")
    }

    override fun editTask(task: Task) {
        TODO("Not yet implemented")
    }


}