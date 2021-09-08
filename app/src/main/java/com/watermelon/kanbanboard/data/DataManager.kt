package com.watermelon.kanbanboard.data

import com.watermelon.kanbanboard.data.domain.Task

object DataManager {
    private val todoTasksList = mutableListOf<Task>()
    private val inProgressTasksList = mutableListOf<Task>()
    private val doneTasksList = mutableListOf<Task>()

    fun addTodoTask(task: Task) = todoTasksList.add(task)
    fun addInProgressTask(task: Task) = inProgressTasksList.add(task)
    fun addDoneTask(task: Task) = doneTasksList.add(task)

    val todoList: List<Task>
        get() = todoTasksList.toList()

    val inProgressList: List<Task>
        get() = inProgressTasksList.toList()

    val doneList: List<Task>
        get() = doneTasksList.toList()
}