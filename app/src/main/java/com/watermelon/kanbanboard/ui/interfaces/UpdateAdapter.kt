package com.watermelon.kanbanboard.ui.interfaces

import com.watermelon.kanbanboard.data.domain.Task

interface UpdateAdapter {
    fun update()
    fun editTask(task: Task)
}