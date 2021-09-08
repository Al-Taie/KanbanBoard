package com.watermelon.kanbanboard.ui.add

import android.content.ContentValues
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
import com.watermelon.kanbanboard.ui.interfaces.UpdateAdapter
import com.watermelon.kanbanboard.util.Constant
import com.watermelon.kanbanboard.util.lastId


class AddFragment(private val listener: CustomDialogFragment,
                  private val updateListener: UpdateAdapter,
                  private val fragmentType: String,
                  private val _task: Task?
) : DialogFragment() {
    val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentAddBinding
        get() = FragmentAddBinding::inflate

    private lateinit var binding: FragmentAddBinding
    private lateinit var dbHelper : TaskDbHelper

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
        dbHelper = TaskDbHelper(requireContext())
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
            title.setText(_task?.title)
            description.setText(_task?.description)
            assignTo.setText(_task?.assignedTo)
            dateViewer.text = _task?.dueDate
            when(_task?.status) {
                Status.CODE -> statusChipGroup.check(R.id.code_chip)
                else -> statusChipGroup.check(R.id.design_chip)
            }
        }
    }

    private fun editTask() {
        binding.apply {
            val status = when (statusChipGroup.checkedChipId) {
                R.id.code_chip -> Status.CODE
                else -> Status.DESIGN
            }
            val editedTask = Task(
                id = requireNotNull(_task?.id),
                title = title.text.toString(),
                description = description.text.toString(),
                assignedTo = assignTo.text.toString(),
                status = status,
                dueDate = dateViewer.text.toString(),
                tableName = _task?.tableName.toString(),
                expanded = false

            )
            val newEntry = ContentValues().apply {
                with(TaskDbHelper.DB) {
                    put(TITLE, title.text.toString())
                    put(DESCRIPTION, description.text.toString())
                    put(ASSIGN_TO, assignTo.text.toString())
                    put(STATUS, status)
                    put(DATE, dateViewer.text.toString())
                    put(TABLE_NAME, _task?.tableName)
                    put(EXPANDED, false)

                }
            }
            dbHelper.writableDatabase.update(_task?.tableName,newEntry,"id = ?",arrayOf(_task?.id.toString()))
            DataManager.replaceTask(oldTask = requireNotNull(_task), newTask = editedTask, to = _task.tableName)
            updateListener.update()

        }

    }

    private fun addTask() {
        val task: Task
        binding.apply {
            val status = when (statusChipGroup.checkedChipId) {
                R.id.code_chip -> Status.CODE
                else -> Status.DESIGN
            }

            val id = DataManager.todoList.lastId()

            task = Task(
                id = id,
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