package com.example.mynotes20.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MediaDao {
    @Query("SELECT * from media ORDER BY id ASC")
    fun getAllMedia(): Flow<List<Media>>

    @Query("SELECT * from media WHERE id = :id")
    fun getMedia(id: Int): Flow<Media>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Media)

    @Update
    suspend fun update(item: Media)

    @Delete
    suspend fun delete(item: Media)
}