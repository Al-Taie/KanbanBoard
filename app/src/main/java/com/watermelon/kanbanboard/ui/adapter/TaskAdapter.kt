package com.watermelon.kanbanboard.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.watermelon.kanbanboard.R
import com.watermelon.kanbanboard.data.domain.Task
import com.watermelon.kanbanboard.databinding.ItemTaskCardBinding

class TaskAdapter(private val list: List<Task>) :
    RecyclerView.Adapter<TaskAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val item = R.layout.item_task_card
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
            }
        }
    }

    override fun getItemCount() = list.size

    class ItemViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        val binding = ItemTaskCardBinding.bind(viewItem)
    }
}