package ru.ilusha.mycalendar

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("SELECT * FROM tasks ORDER BY date ASC, time ASC")
    fun getAll(): Flow<List<Task>> // Убрали suspend, добавили Flow

    @Query("SELECT * FROM tasks WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): Task?

    @Query("SELECT * FROM tasks WHERE date = :date")
    suspend fun getByDate(date: String): List<Task>



}