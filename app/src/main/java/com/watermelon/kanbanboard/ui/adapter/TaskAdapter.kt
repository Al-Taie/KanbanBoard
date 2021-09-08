package com.watermelon.kanbanboard.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.watermelon.kanbanboard.R
import com.watermelon.kanbanboard.data.domain.Task
import com.watermelon.kanbanboard.databinding.ItemTaskCardBinding
import com.watermelon.kanbanboard.ui.diff_util.TaskDiffUtil
import com.watermelon.kanbanboard.ui.interfaces.UpdateAdapter
import com.watermelon.kanbanboard.util.CustomSpinnerItem
import com.watermelon.kanbanboard.util.initData


class TaskAdapter(private var list: List<Task>, private val listener: UpdateAdapter) :
    RecyclerView.Adapter<TaskAdapter.ItemViewHolder>() {
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val item = R.layout.item_task_card
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.apply {
            with(list[position]) {
                textTitle.text = title
                textStatus.text = status
                textPersonName.text = assignedTo
                textDeadline.text = dueDate
                textDescription.text = description
                buttonEditTask.setOnClickListener { listener.editTask(this) }
            }


            Toast.makeText(context, position.toString(), Toast.LENGTH_LONG).show()
                initSpinner(spinnerTaskCard)
        }
    }

    private fun initSpinner(spinner: Spinner) {
        val taskCardSpinner: Spinner = spinner
        val customItemList = arrayListOf<CustomSpinnerItem>().initData()

        taskCardSpinner.apply {
            adapter = CustomSpinnerAdapter(context, customItemList)
            object: AdapterView.OnItemSelectedListener {
                override fun onItemSelected(adapterView: AdapterView<*>, p1: View?, p2: Int, p3: Long) {
                    dropDownWidth = findViewById<LinearLayout>(R.id.custom_spinner_item_layout).width
                    val item = adapterView.selectedItem as CustomSpinnerItem
                    item.tableName
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {
                    // TODO("Not yet implemented")
                }
            }
        }
    }

    fun setData(newList: List<Task>){
        val diffResult = DiffUtil.calculateDiff(TaskDiffUtil(oldList = list, newList = newList))
        list = newList
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = list.size

    class ItemViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        val binding = ItemTaskCardBinding.bind(viewItem)
    }
}