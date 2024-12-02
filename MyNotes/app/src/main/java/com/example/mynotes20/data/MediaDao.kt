package com.example.mynotes20.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MediaDao {
    @Query("SELECT * from media")
    fun getAllMedia(): Flow<List<Media>>

    @Query("SELECT * from media WHERE id = :id")
    fun getMedia(id: Int): Flow<Media>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(media: Media)

    /*@Transaction
    suspend fun insertMediaWithForeignKey(media: Media, noteId: Int) {
        // First, verify if the noteId exists in the Notes table
        val note = NoteDao.getNote(noteId)
        if (note != null) {
            // If the noteexists, insert the media
            media.noteId = noteId
            insert(media)
        } else {
            // Handle the case where the noteId doesn't exist
            // You might want to throw an exception or log an error
        }
    }*/

    @Update
    suspend fun update(media: Media)

    @Delete
    suspend fun delete(media: Media)
}