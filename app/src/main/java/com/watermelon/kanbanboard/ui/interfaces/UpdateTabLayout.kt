package com.watermelon.kanbanboard.ui.interfaces

import com.watermelon.kanbanboard.data.domain.Task

interface UpdateTabLayout {
    fun update()
    fun moveTaskInDatabase(task: Task, to: String)
    fun deleteTask(task: Task)
}