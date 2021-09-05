package com.watermelon.kanbanboard.ui

import android.content.ContentValues
import android.view.LayoutInflater
import com.watermelon.kanbanboard.R
import com.watermelon.kanbanboard.data.TaskDbHelper
import com.watermelon.kanbanboard.databinding.ActivityMainBinding
import com.watermelon.kanbanboard.ui.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var databaseHelper: TaskDbHelper
    override val theme = R.style.Theme_KanbanBoard

    override fun setup() {
        databaseHelper = TaskDbHelper(applicationContext)
    }

    override fun callBack() {
        addTask()
    }

    private fun addTask() {
        val newEntry = ContentValues()

        with(TaskDbHelper.DB) {
            newEntry.apply {
                put(TITLE, "TEST")
                put(DESCRIPTION, "TEST")
                put(STATUS, "TEST")
                put(DATE, "2021-09-05")
            }
            databaseHelper.writableDatabase.insert(TABLE_NAME, null, newEntry)
        }
    }

    override val inflate: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate
}