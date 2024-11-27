package com.example.mynotes20.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RemindersDao {
    @Query("SELECT * from reminders ORDER BY date ASC")
    fun getAllTasks(): Flow<List<Reminders>>

    @Query("SELECT * from reminders WHERE id = :id")
    fun getTasks(id: Int): Flow<Reminders>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Reminders)

    @Update
    suspend fun update(item: Reminders)

    @Delete
    suspend fun delete(item: Task)
}