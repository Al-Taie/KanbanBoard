package com.watermelon.kanbanboard.data.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.watermelon.kanbanboard.data.DataManager
import com.watermelon.kanbanboard.data.domain.Task
import com.watermelon.kanbanboard.util.Constant


class TaskDbHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
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

        if (cursor.moveToFirst())
            parseData(cursor)

        while (cursor.moveToNext()) {
            parseData(cursor)
        }
    }

    fun write(task: Task) {
        val newEntry = ContentValues()
        task.apply {
            initData(table = tableName, task)
            newEntry.apply {
                with(DB) {
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

    fun edit(task: Task) {
        Log.v("EDIT_TITLE","begin edit inside helper = ${task.title}")

        val newEntry = ContentValues().apply {
            with(DB) {
                put(TITLE, task.title)
                put(DESCRIPTION, task.description)
                put(ASSIGN_TO, task.assignedTo)
                put(STATUS, task.status)
                put(DATE, task.dueDate)
                put(EXPANDED, false)

            }
        }
        Log.v("EDIT_TITLE","end edit inside helper = ${task.title}")
        writableDatabase.update(task.tableName,newEntry,"id = ?",arrayOf(task.id.toString()))



    }


    fun move(task: Task, to : String) {
        TABLES.apply {
            when (task.tableName) {
                TO_DO -> writableDatabase.delete(TO_DO,"id = ?", arrayOf(task.id.toString()))
                IN_PROGRESS -> writableDatabase.delete(IN_PROGRESS,"id = ?", arrayOf(task.id.toString()))
                else -> writableDatabase.delete(DONE,"id = ?", arrayOf(task.id.toString()))
            }

            task.tableName = to

                task.apply {
                    val newEntry = ContentValues().apply {
                    with(DB) {
                        put(TITLE, title)
                        put(DESCRIPTION, description)
                        put(ASSIGN_TO, assignedTo)
                        put(STATUS, status)
                        put(DATE, dueDate)
                        put(TABLE_NAME, tableName)
                        put(EXPANDED, expanded)
                    }
                }
                    when (tableName) {
                        TO_DO -> writableDatabase.insert(TO_DO,null,newEntry)
                        IN_PROGRESS -> writableDatabase.insert(IN_PROGRESS,null,newEntry)
                        else -> writableDatabase.insert(DONE,null,newEntry)
                    }
            }

        }
    }

    private fun parseData(cursor: Cursor) {
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
            initData(table = tableName, task = task)
        }
    }

    private fun initData(table: String, task: Task) {
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