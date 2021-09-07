package com.watermelon.kanbanboard.util

import com.watermelon.kanbanboard.R

fun ArrayList<CustomSpinnerItem>.getCustomItemList(): ArrayList<CustomSpinnerItem>{
    this.add(CustomSpinnerItem("To Do", R.drawable.ic_todo))
    this.add(CustomSpinnerItem("In Progress", R.drawable.ic_in_progress))
    this.add(CustomSpinnerItem("Done", R.drawable.ic_done))
    return this
}