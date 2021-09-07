package com.watermelon.kanbanboard.data.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.watermelon.kanbanboard.data.DataManager
import com.watermelon.kanbanboard.data.domain.Task
import com.watermelon.kanbanboard.util.Constant

class TaskDbHelper(val context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(database: SQLiteDatabase?) {
        TABLES.list.forEach { table ->
            val sql = "CREATE TABLE $table (" +
                    "${DB.ID} INTEGER PRIMARY KEY," +
                    "${DB.TITLE} TEXT," +
                    "${DB.DESCRIPTION} TEXT," +
                    "${DB.ASSIGN_TO} TEXT," +
                    "${DB.STATUS} TEXT," +
                    "${DB.DATE} TEXT," +
                    "${DB.TABLE_NAME} TEXT," +
                    "${DB.EXPANDED} INTEGER)"
            database?.execSQL(sql)
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}

    fun read(table: String) {
        Log.v("READ_FUNCTION", "begin read")
        val sql = "SELECT * FROM $table"
        val cursor = readableDatabase.rawQuery(sql, arrayOf<String>())
        Toast.makeText(context, cursor.moveToNext().toString(), Toast.LENGTH_LONG).show()
        while (cursor.moveToNext()) {
            val task = parseData(cursor)
            initData(table = table, task = task)
        }
    }

    fun write(task: Task) {
        val newEntry = ContentValues()
        task.apply {
            initData(table = tableName, task)
            newEntry.apply {
                with(DB) {
                    if (id != 0)
                        put(ID, id)
                    put(TITLE, title)
                    put(DESCRIPTION, description)
                    put(ASSIGN_TO, assignedTo)
                    put(STATUS, status)
                    put(DATE, dueDate)
                    put(TABLE_NAME, tableName)
                    put(EXPANDED, expanded)
                }
            }

            writableDatabase.insert(tableName, null, newEntry)
        }

    }

    private fun parseData(cursor: Cursor): Task {
        val task: Task
        cursor.apply {
            val id = getInt(Constant.Index.ID)
            val title = getString(Constant.Index.TITLE)
            val tableName = getString(Constant.Index.TABLE_NAME)
            val description = getString(Constant.Index.DESCRIPTION)
            val status = getString(Constant.Index.STATUS)
            val assignedTo = getString(Constant.Index.ASSIGNED_TO)
            val dueDate = getString(Constant.Index.DUE_DATE)
            val expanded = getInt(Constant.Index.EXTENDED).toString().toBoolean()

            task = Task(
                id = id,
                title = title,
                tableName = tableName,
                description = description,
                status = status,
                assignedTo = assignedTo,
                dueDate = dueDate,
                expanded = expanded
            )
            initData(table = tableName,task = task)
        }
        return task
    }

    private fun initData(table: String, task: Task) {
        Log.v("READ_TRACE", "$table - ${task.assignedTo}")
        when (table) {
            TABLES.TO_DO -> DataManager.addTodoTask(task)
            TABLES.IN_PROGRESS -> DataManager.addInProgressTask(task)
            TABLES.DONE -> DataManager.addDoneTask(task)
        }
    }

    companion object {
        private const val DB_NAME = "TasksDatabase"
        private const val DB_VERSION = 1
    }

    object TABLES {
        const val TO_DO = "todo"
        const val IN_PROGRESS = "inProgress"
        const val DONE = "done"
        val list = listOf(TO_DO, IN_PROGRESS, DONE)
    }

    object DB {
        const val ASSIGN_TO = "assignTo"
        const val ID = "id"
        const val TITLE = "title"
        const val TABLE_NAME = "tableName"
        const val DESCRIPTION = "description"
        const val STATUS = "status"
        const val DATE = "date"
        const val EXPANDED = "expanded"
    }
}