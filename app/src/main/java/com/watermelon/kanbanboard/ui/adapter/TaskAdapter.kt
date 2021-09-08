package com.watermelon.kanbanboard.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.watermelon.kanbanboard.R
import com.watermelon.kanbanboard.data.DataManager
import com.watermelon.kanbanboard.data.database.TaskDbHelper.TABLES
import com.watermelon.kanbanboard.data.domain.Task
import com.watermelon.kanbanboard.databinding.ItemTaskCardBinding
import com.watermelon.kanbanboard.ui.diff_util.TaskDiffUtil
import com.watermelon.kanbanboard.ui.interfaces.UpdateAdapter
import com.watermelon.kanbanboard.ui.interfaces.UpdateTabLayout


class TaskAdapter(
    private var list: List<Task>,
    private val listener: UpdateAdapter,
    private val updateTabLayoutListener: UpdateTabLayout
) :
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

                val spinnerDrawAbles = arrayOf(
                    R.drawable.ic_checklist,
                    R.drawable.ic_done,
                    R.drawable.ic_in_progress
                )

                val spinnerIcons = spinnerDrawAbles.map {
                    ResourcesCompat.getDrawable(
                        root.resources, it,
                        null
                    )
                }

                var startIconDrawable = when (tableName) {
                    TABLES.TO_DO -> spinnerIcons[0]
                    TABLES.DONE -> spinnerIcons[1]
                    else -> spinnerIcons[2]
                }

                spinner.startIconDrawable = startIconDrawable

                setStatus.setOnItemClickListener { _, _, i, _ ->
                    startIconDrawable = spinnerIcons[i]
                    spinner.startIconDrawable = startIconDrawable
                    val selectedTable = TABLES.list[i]
                    DataManager.moveTask(this, selectedTable, updateTabLayoutListener)
                    listener.update()
                }
            }
            val items = root.resources.getStringArray(R.array.fragments_names)
            val adapter = ArrayAdapter(context, R.layout.status_dropdown_item, items)
            setStatus.setAdapter(adapter)
        }
    }

    fun setData(newList: List<Task>) {
        val diffResult = DiffUtil.calculateDiff(TaskDiffUtil(oldList = list, newList = newList))
        list = newList
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = list.size

    class ItemViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        val binding = ItemTaskCardBinding.bind(viewItem)
    }
}