package com.example.mynotes20.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * from tasks ORDER BY dateComplete ASC")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * from tasks WHERE id = :id")
    fun getTasks(id: Int): Flow<Task>

    @Query("SELECT * FROM mediaTasks WHERE taskId = :taskId")
    fun getMediaForTask(taskId: Int): Flow<List<MediaTask>>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Task): Long

    @Update
    suspend fun update(item: Task)

    @Delete
    suspend fun delete(item: Task)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMediaTask(mediaTask: MediaTask)
}