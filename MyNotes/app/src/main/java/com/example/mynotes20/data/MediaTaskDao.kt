package com.example.mynotes20.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MediaTaskDao {
    @Query("SELECT * from mediaTasks ORDER BY id ASC")
    fun getAllMedia(): Flow<List<MediaTask>>

    @Query("SELECT * from mediaTasks WHERE id = :id")
    fun getMedia(id: Int): Flow<MediaTask>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mediaTask: MediaTask)

    @Update
    suspend fun update(mediaTask: MediaTask)

    @Delete
    suspend fun delete(mediaTask: MediaTask)
}