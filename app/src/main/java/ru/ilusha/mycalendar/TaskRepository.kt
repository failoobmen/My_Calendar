package ru.ilusha.mycalendar

object TaskRepository {
    val tasks: MutableList<Task> = mutableListOf(
        Task(id = 1, title = "Купить продукты", description = "Молоко, хлеб", date = "10.10.2024", isDone = false),
        Task(id = 2, title = "Сделать лабу", description = "По сетям", date = "11.10.2024", isDone = true),
        Task(id = 3, title = "Позвонить", description = "Срочно", date = "12.10.2024", isDone = false)
    )

    fun getById(id: Int): Task? = tasks.firstOrNull { it.id == id }
}