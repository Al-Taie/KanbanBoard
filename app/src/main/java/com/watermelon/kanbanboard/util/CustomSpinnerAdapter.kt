package com.watermelon.kanbanboard.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.watermelon.kanbanboard.R

class CustomSpinnerAdapter(context: Context, customList: ArrayList<CustomSpinnerItem>) :
    ArrayAdapter<CustomSpinnerItem>(context, 0, customList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val convertView = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.custom_spinner_layout, parent, false)
        val item = getItem(position)
        val spinnerIcon = convertView.findViewById<ImageView>(R.id.icon_spinner_layout)
        val spinnerText = convertView.findViewById<TextView>(R.id.text_spinner_layout)
        item?.let {
            spinnerIcon.setImageResource(it.spinnerItemImage)
            spinnerText.text = it.spinnerItemName
        }
        return convertView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val convertView = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.custom_task_card_spinner_item, parent, false)
        val item = getItem(position)
        val spinnerItemIcon = convertView.findViewById<ImageView>(R.id.icon_spinner_item)
        val spinnerItemText = convertView.findViewById<TextView>(R.id.text_spinner_item)
        item?.let {
            spinnerItemIcon.setImageResource(it.spinnerItemImage)
            spinnerItemText.text = it.spinnerItemName
        }
        return convertView
    }
}