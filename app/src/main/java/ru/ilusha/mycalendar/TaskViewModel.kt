package ru.ilusha.mycalendar

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = TaskRepository(application.applicationContext)

    // StateFlow для автоматического обновления списка
    val tasks: StateFlow<List<Task>> = repository.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addTask(title: String, description: String, date: String, time: String) {
        viewModelScope.launch {
            repository.insert(Task(title = title, description = description, date = date, time = time))
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.update(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.delete(task)
        }
    }

    // Для тестового заполнения (по замечанию преподавателя)
    fun seedTestTasks() {
        viewModelScope.launch {
            repository.insert(Task(title = "Тест 1", description = "Из ViewModel", date = "01.11.2024", time = "10:00"))
            repository.insert(Task(title = "Тест 2", description = "Проверка Flow", date = "02.11.2024", time = "12:00"))
        }
    }
}