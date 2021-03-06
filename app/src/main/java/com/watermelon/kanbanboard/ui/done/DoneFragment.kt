package com.watermelon.kanbanboard.ui.done

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.watermelon.kanbanboard.data.DataManager
import com.watermelon.kanbanboard.data.domain.Task
import com.watermelon.kanbanboard.databinding.FragmentDoneBinding
import com.watermelon.kanbanboard.ui.adapter.TaskAdapter
import com.watermelon.kanbanboard.ui.add.AddFragment
import com.watermelon.kanbanboard.ui.base.BaseFragment
import com.watermelon.kanbanboard.ui.interfaces.CustomDialogFragment
import com.watermelon.kanbanboard.ui.interfaces.UpdateAdapter
import com.watermelon.kanbanboard.ui.interfaces.UpdateTabLayout
import com.watermelon.kanbanboard.util.Constant

class DoneFragment(
    private val listener: CustomDialogFragment,
    private val updateTabLayoutListener: UpdateTabLayout
) : BaseFragment<FragmentDoneBinding>(), UpdateAdapter {

    override val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentDoneBinding
        get() = FragmentDoneBinding::inflate

    lateinit var adapter: TaskAdapter

    override fun setup() {
        updateRecycler()

    }

    override fun callBack() {

    }

    override fun onResume() {
        super.onResume()
        adapter.setData(DataManager.doneList)
    }

    override fun update() {
        Log.v("TEST_UPDATE", "UPDATE DONE")
        adapter.setData(DataManager.doneList)
        updateTabLayoutListener.update()
    }

    override fun editTask(task: Task) {
        listener.showDialog(AddFragment(listener, this, Constant.FragmentType.EDIT, task))
    }

    private fun updateRecycler() {
        adapter = TaskAdapter(DataManager.doneList, this, updateTabLayoutListener)
        binding.doneRecycler.adapter = adapter
    }
}