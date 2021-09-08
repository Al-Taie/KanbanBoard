package com.watermelon.kanbanboard.util

import com.watermelon.kanbanboard.data.domain.Task

/**
 * @param index Integer or Null
 * @param task Task Object
 * @author     Ahmed Mones
 * @return     Unit
 * */
fun MutableList<Task>.add(index: Int?, task: Task) {
    if (index == null) {
        this.add(task)
    } else {
        this.add(index, task)
    }
}

/**
 * return last task database id
 * @author     Ahmed Mones
 * @return     Int
 * */
fun List<Task>.lastId(): Int {
    return try {
        this.last().id + 1
    } catch (e: Exception) {
        1
    }
}