package com.watermelon.kanbanboard.ui.diff_util

import androidx.recyclerview.widget.DiffUtil
import com.watermelon.kanbanboard.data.domain.Task

class TaskDiffUtil(private val oldList: List<Task>, private val newList: List<Task>) :
    DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (
                oldList[oldItemPosition].title == newList[newItemPosition].title
                        && oldList[oldItemPosition].description == newList[newItemPosition].description
                        && oldList[oldItemPosition].assignedTo == newList[newItemPosition].assignedTo
                        && oldList[oldItemPosition].dueDate == newList[newItemPosition].dueDate
                        && oldList[oldItemPosition].status == newList[newItemPosition].status
                )
    }
}