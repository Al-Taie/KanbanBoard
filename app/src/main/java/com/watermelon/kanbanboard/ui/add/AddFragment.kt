package com.watermelon.kanbanboard.ui.add

import android.content.ContentValues
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.watermelon.kanbanboard.R
import com.watermelon.kanbanboard.data.TaskDbHelper
import com.watermelon.kanbanboard.databinding.FragmentAddBinding
import com.watermelon.kanbanboard.ui.interfaces.CustomDialogFragment


class AddFragment(private val listener: CustomDialogFragment) : DialogFragment() {
    private val databaseHelper by lazy { context?.let { TaskDbHelper(it) } }
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
        val items = resources.getStringArray(R.array.members_names)
        val adapter = ArrayAdapter(requireContext(), R.layout.assignto_dropdown_item, items)
        binding.assignTo.setAdapter(adapter)
    }

    private fun callBack() {
        binding.apply {
            dateView.setOnClickListener { listener.showDatePicker() }
            pickDateButton.setOnClickListener { listener.showDatePicker() }
            addButton.setOnClickListener {
                addTask()
                listener.closeDialog(this@AddFragment)
            }
        }
    }

    private fun addTask() {
        val newEntry = ContentValues()

        with(TaskDbHelper.DB) {
            newEntry.apply {
                binding.apply {
                    put(TITLE, title.text.toString())
                    put(DESCRIPTION, description.text.toString())

                    when (statusChipGroup.checkedChipId) {
                        R.id.code_chip -> put(STATUS, CODE)
                        R.id.design_chip -> put(STATUS, DESIGN)
                    }
                    put(DATE, dateViewer.text.toString())
                    put(EXPANDED, false)
                }
            }
            databaseHelper?.apply {
                writableDatabase.insert(TABLE_NAME, null, newEntry)
            }
        }
    }

    companion object Status {
        const val DESIGN = "design"
        const val CODE = "code"
    }
}