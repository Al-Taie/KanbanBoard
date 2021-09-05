package com.watermelon.kanbanboard.ui.data

data class Task(
    val title: String,
    val description: String,
    val assignedTo: String,
    val dueDate: String,
    val status: String
)