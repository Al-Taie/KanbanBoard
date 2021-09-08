package com.watermelon.kanbanboard.util

import com.watermelon.kanbanboard.R
import com.watermelon.kanbanboard.data.database.TaskDbHelper.TABLES
import com.watermelon.kanbanboard.data.domain.Task

/**
 * @author     Zainab Mahmood
 * @return     ArrayList<CustomSpinnerItem>
 * */
fun ArrayList<CustomSpinnerItem>.initData(): ArrayList<CustomSpinnerItem> {
    this.add(CustomSpinnerItem(TABLES.TO_DO, "To Do", R.drawable.ic_todo))
    this.add(CustomSpinnerItem(TABLES.IN_PROGRESS, "In Progress", R.drawable.ic_in_progress))
    this.add(CustomSpinnerItem(TABLES.DONE, "Done", R.drawable.ic_done))
    return this
}

/**
 * @param index Integer or Null
 * @param task Task Object
 * @author     Ahmed Mones
 * @return     Unit
 * */
fun MutableList<Task>.add(index: Int?, task: Task) {
    if (index == null)
        this.add(task)
    else
        this.add(index, task)
}

/**
 * return last task database id
 * @author     Ahmed Mones
 * @return     Int
 * */
fun List<Task>.lastId() : Int {
    return this.last().id + 1
}