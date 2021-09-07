package com.watermelon.kanbanboard.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.watermelon.kanbanboard.R
import com.watermelon.kanbanboard.data.DataManager
import com.watermelon.kanbanboard.data.database.TaskDbHelper
import com.watermelon.kanbanboard.data.domain.Task
import com.watermelon.kanbanboard.databinding.FragmentAddBinding
import com.watermelon.kanbanboard.ui.interfaces.CustomDialogFragment


class AddFragment(private val listener: CustomDialogFragment) : DialogFragment() {
    val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentAddBinding
        get() = FragmentAddBinding::inflate

    private lateinit var binding: FragmentAddBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = inflate(inflater, container, false)
        setup()
        callBack()
        return binding.root
    }

    private fun setup() {}

    private fun callBack() {
        binding.apply {
            dateView.setOnClickListener { listener.showDatePicker() }
            pickDateButton.setOnClickListener { listener.showDatePicker() }
            addButton.setOnClickListener {
                addTask()
                listener.closeDialog(this@AddFragment)
            }
        }

        val items = resources.getStringArray(R.array.members_names)
        val adapter = ArrayAdapter(requireContext(), R.layout.assignto_dropdown_item, items)
        binding.assignTo.setAdapter(adapter)
    }

    private fun addTask() {
        val task: Task
        binding.apply {
            val status = when (statusChipGroup.checkedChipId) {
                R.id.code_chip -> CODE
                else -> DESIGN
            }

            task = Task(
                title = title.text.toString(),
                tableName = TaskDbHelper.TABLES.TO_DO,
                description = description.text.toString(),
                status = status,
                assignedTo = assignTo.text.toString(),
                dueDate = dateViewer.text.toString(),
                expanded = false
            )
        }

        DataManager.addTodoTask(task = task)
    }

    companion object Status {
        const val DESIGN = "design"
        const val CODE = "code"
    }
}