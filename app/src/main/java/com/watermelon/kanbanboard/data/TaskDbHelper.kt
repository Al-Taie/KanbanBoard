package com.watermelon.kanbanboard.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TaskDbHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(database: SQLiteDatabase?) {
        val sql = "CREATE TABLE ${DB.TABLE_NAME} (" +
                "${DB.ID} INTEGER PRIMARY KEY," +
                "${DB.TITLE} TEXT," +
                "${DB.DESCRIPTION} TEXT," +
                "${DB.STATUS} TEXT," +
                "${DB.DATE} TEXT)"
        database?.execSQL(sql)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}

    companion object {
        private const val DB_NAME = "TasksDatabase"
        private const val DB_VERSION = 1
    }

    object DB {
        const val TABLE_NAME = "TASKS"
        const val ID = "id"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val STATUS = "status"
        const val DATE = "date"
    }
}