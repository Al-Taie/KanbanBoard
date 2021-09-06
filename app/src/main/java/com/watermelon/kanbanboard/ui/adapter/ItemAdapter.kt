package com.watermelon.kanbanboard.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.watermelon.kanbanboard.R
import com.watermelon.kanbanboard.databinding.ItemTaskCardBinding

class ItemAdapter(private val list: List<String>): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task_card,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentData = list[position]
        holder.binding.apply{
            textHighlight.text = "Design"
            textPersonName.text = "name"
            textDeadline.text = "Date"
            textTaskDescription.text = "Description"
        }
    }
    override fun getItemCount() = list.size

    class ItemViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        val binding = ItemTaskCardBinding.bind(viewItem)
    }
}