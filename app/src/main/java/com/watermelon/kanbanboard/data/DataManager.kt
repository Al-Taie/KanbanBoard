package com.watermelon.kanbanboard.data

import com.watermelon.kanbanboard.data.database.TaskDbHelper.TABLES
import com.watermelon.kanbanboard.data.domain.Task
import com.watermelon.kanbanboard.ui.interfaces.UpdateTabLayout
import com.watermelon.kanbanboard.util.add

object DataManager {
    private val todoTasksList = mutableListOf<Task>()
    private val inProgressTasksList = mutableListOf<Task>()
    private val doneTasksList = mutableListOf<Task>()

    /**
     * @param task Task Object
     * @param index Integer or Null
     * @author     Ahmed Mones
     * @return     Int
     * */
    fun addTodoTask(task: Task, index: Int? = null) = todoTasksList.add(index, task)

    /**
     * @param task Task Object
     * @param index Integer or Null
     * @author     Ahmed Mones
     * @return     Int
     * */
    fun addInProgressTask(task: Task, index: Int? = null) = inProgressTasksList.add(index, task)

    /**
     * @param task Task Object
     * @param index Integer or Null
     * @author     Ahmed Mones
     * @return     Int
     * */
    fun addDoneTask(task: Task, index: Int? = null) = doneTasksList.add(index, task)

    /**
     * @param oldTask Task Object
     * @param newTask Task Object
     * @param to      Table Name
     * @author     Ahmed Mones
     * @return     Unit
     * */
    fun replaceTask(oldTask: Task, newTask: Task, to: String) {
        TABLES.apply {
            val position = getPosition(oldTask)

            when (to) {
                TO_DO -> todoTasksList[position] = newTask
                IN_PROGRESS -> inProgressTasksList[position] = newTask
                DONE -> doneTasksList[position] = newTask
            }
        }
    }

    /**
     * @param task Task Object
     * @param to      Table Name
     * @author     Ahmed Mones
     * @return     Unit
     * */
    fun moveTask(task: Task, to: String, updateTabLayoutListener: UpdateTabLayout) {
        TABLES.apply {
            val position = getPosition(task = task)

            updateTabLayoutListener.moveTaskInDatabase(task, to)
            task.tableName = to

            when (to) {
                TO_DO -> todoTasksList[position] = task
                IN_PROGRESS -> inProgressTasksList[position] = task
                DONE -> doneTasksList[position] = task
            }
        }
        updateTabLayoutListener.update()
    }

    /**
     * @param task Task Object
     * @author     Ahmed Mones
     * @return     Int
     * */
    private fun getPosition(task: Task) = when (task.tableName) {
        TABLES.TO_DO -> todoTasksList.indexOf(task)
        TABLES.IN_PROGRESS -> inProgressTasksList.indexOf(task)
        else -> doneTasksList.indexOf(task)
    }

    val todoList: List<Task>
        get() = todoTasksList.toList()

    val inProgressList: List<Task>
        get() = inProgressTasksList.toList()

    val doneList: List<Task>
        get() = doneTasksList.toList()
}