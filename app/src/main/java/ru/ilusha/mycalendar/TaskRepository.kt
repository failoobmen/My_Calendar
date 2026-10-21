package ru.ilusha.mycalendar

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class TaskRepository(context: Context) {

    private val dao: TaskDao = AppDatabase.getInstance(context).taskDao()

    fun getAll(): Flow<List<Task>> = dao.getAll().flowOn(Dispatchers.IO)

    suspend fun insert(task: Task) = withContext(Dispatchers.IO) {
        dao.insert(task)
    }

    suspend fun update(task: Task) = withContext(Dispatchers.IO) {
        dao.update(task)
    }

    suspend fun delete(task: Task) = withContext(Dispatchers.IO) {
        dao.delete(task)
    }

    suspend fun getById(id: Int): Task? = withContext(Dispatchers.IO) {
        dao.getById(id)
    }

    suspend fun getByDate(date: String): List<Task> = withContext(Dispatchers.IO) {
        dao.getByDate(date)
    }
}