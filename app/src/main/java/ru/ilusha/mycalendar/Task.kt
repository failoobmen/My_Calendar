package ru.ilusha.mycalendar

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val date: String,
    val isDone: Boolean = false
)