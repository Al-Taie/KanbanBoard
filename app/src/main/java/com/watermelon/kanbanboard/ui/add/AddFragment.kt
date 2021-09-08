package com.watermelon.kanbanboard.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.watermelon.kanbanboard.R
import com.watermelon.kanbanboard.data.database.TaskDbHelper
import com.watermelon.kanbanboard.data.domain.Task
import com.watermelon.kanbanboard.databinding.FragmentAddBinding
import com.watermelon.kanbanboard.ui.interfaces.CustomDialogFragment
import com.watermelon.kanbanboard.ui.interfaces.UpdateAdapter
import com.watermelon.kanbanboard.util.Constant


class AddFragment(private val listener: CustomDialogFragment, private val updateListener: UpdateAdapter, private val fragmentType: String, val task: Task?) : DialogFragment() {
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

    private fun setup() {
    }

    private fun callBack() {
        binding.apply {
            dateView.setOnClickListener { listener.showDatePicker() }
            pickDateButton.setOnClickListener { listener.showDatePicker() }
            when(fragmentType){

                Constant.FragmentType.ADD -> {
                    addButton.text = Constant.FragmentType.ADD
                    addNewTaskHeadline.text = Title.ADD
                    addButton.setOnClickListener {
                        addTask()
                        listener.closeDialog(this@AddFragment)
                    }
                }

                Constant.FragmentType.EDIT -> {
                    bindTaskData()
                    addButton.setOnClickListener{
                        editTask()
                        listener.closeDialog(this@AddFragment)
                    }
                }
            }

        }

        val items = resources.getStringArray(R.array.members_names)
        val adapter = ArrayAdapter(requireContext(), R.layout.assignto_dropdown_item, items)
        binding.assignTo.setAdapter(adapter)
    }

    private fun bindTaskData() {
        binding.apply {
            addButton.text = Constant.FragmentType.EDIT
            addNewTaskHeadline.text = Title.EDIT
            title.setText(task?.title)
            description.setText(task?.description)
            assignTo.setText(task?.assignedTo)
            dateViewer.text = task?.dueDate
            when(task?.status) {
                Status.CODE -> statusChipGroup.check(R.id.code_chip)
                else -> statusChipGroup.check(R.id.design_chip)
            }
        }
    }

    private fun editTask() {

    }

    private fun addTask() {
        val task: Task
        val dbHelper = TaskDbHelper(requireContext())
        binding.apply {
            val status = when (statusChipGroup.checkedChipId) {
                R.id.code_chip -> Status.CODE
                else -> Status.DESIGN
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
        dbHelper.write(task)
        updateListener.update()
    }

    companion object
        object Status {
            const val DESIGN = "design"
            const val CODE = "code"
        }
    object Title {
        const val ADD = "Add new Task"
        const val EDIT = "Edit Task"
    }

}