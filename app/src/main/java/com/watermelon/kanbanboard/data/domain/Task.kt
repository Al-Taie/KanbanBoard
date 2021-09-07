package com.watermelon.kanbanboard.data.domain

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val assignedTo: String,
    val dueDate: String,
    val status: String,
    val tableName: String,
    val expanded: Boolean = false
)
