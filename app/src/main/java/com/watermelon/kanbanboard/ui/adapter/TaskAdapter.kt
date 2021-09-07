package com.watermelon.kanbanboard.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.watermelon.kanbanboard.R
import com.watermelon.kanbanboard.databinding.ItemTaskCardBinding
import com.watermelon.kanbanboard.util.CustomSpinnerAdapter
import com.watermelon.kanbanboard.util.CustomSpinnerItem
import com.watermelon.kanbanboard.util.getCustomItemList

class ItemAdapter(private val list: List<String>): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(){
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        context = parent.context
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_task_card, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentData = list[position]
        holder.binding.apply{
            textHighlight.text = "Design"
            textPersonName.text = "name"
            textDeadline.text = "Date"
            textTaskDescription.text = "Description"
            initSpinner(spinnerTaskCard)
        }
    }

    private fun initSpinner(spinner: Spinner) {
        val taskCardSpinner: Spinner = spinner
        val customItemList = arrayListOf<CustomSpinnerItem>().getCustomItemList()
        val spinnerAdapter = CustomSpinnerAdapter(context, customItemList)
        taskCardSpinner.apply {
            adapter = spinnerAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val width = findViewById<LinearLayout>(R.id.custom_spinner_item_layout).width
                    dropDownWidth = width
                    val item = p0?.selectedItem as CustomSpinnerItem
                    Toast.makeText(context, item.spinnerItemName, Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }
        }
    }

    override fun getItemCount() = list.size

    class ItemViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        val binding = ItemTaskCardBinding.bind(viewItem)
    }
}