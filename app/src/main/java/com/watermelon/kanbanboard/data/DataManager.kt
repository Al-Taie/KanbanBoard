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
     * @param task Task Object
     * @author     Ahmed Mones
     * @return     Int
     * */
    private fun removeTodoTask(task: Task) : Int {
        val position = todoTasksList.indexOf(task)
        todoTasksList.remove(task)
        return position
    }

    /**
     * @param task Task Object
     * @author     Ahmed Mones
     * @return     Int
     * */
    private fun removeProgressTask(task: Task) : Int {
        val position = inProgressTasksList.indexOf(task)
        inProgressTasksList.remove(task)
        return position
    }

    /**
     * @param task Task Object
     * @author     Ahmed Mones
     * @return     Int
     * */
    private fun removeDoneTask(task: Task) : Int {
        val position = doneTasksList.indexOf(task)
        doneTasksList.remove(task)
        return position
    }

    /**
     * @param oldTask Task Object
     * @param newTask Task Object
     * @param to      Table Name
     * @author     Ahmed Mones
     * @return     Unit
     * */
    fun replaceTask(oldTask: Task, newTask: Task, to: String) {
        TABLES.apply {
            val position = when (oldTask.tableName) {
                TO_DO -> removeTodoTask(oldTask)
                IN_PROGRESS -> removeProgressTask(oldTask)
                else -> removeDoneTask(oldTask)
            }

            when (to) {
                TO_DO -> addTodoTask(task = newTask, index = position)
                IN_PROGRESS -> addInProgressTask(task = newTask, index = position)
                DONE -> addDoneTask(task = newTask, index = position)
            }
        }
    }

    /**
     * @param task Task Object
     * @param newTask Task Object
     * @param to      Table Name
     * @author     Ahmed Mones
     * @return     Unit
     * */
    fun moveTask(task: Task, to: String, updateTabLayoutListener: UpdateTabLayout) {
        TABLES.apply {
            when (task.tableName) {
                TO_DO -> removeTodoTask(task)
                IN_PROGRESS -> removeProgressTask(task)
                else -> removeDoneTask(task)
            }

            updateTabLayoutListener.moveTaskInDatabase(task, to)
            task.tableName = to

            when (to) {
                TO_DO -> addTodoTask(task = task)
                IN_PROGRESS -> addInProgressTask(task = task)
                DONE -> addDoneTask(task = task)
            }
        }
        updateTabLayoutListener.update()
    }

    val todoList: List<Task>
        get() = todoTasksList.toList()

    val inProgressList: List<Task>
        get() = inProgressTasksList.toList()

    val doneList: List<Task>
        get() = doneTasksList.toList()
}