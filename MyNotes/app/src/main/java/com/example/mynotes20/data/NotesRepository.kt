package com.example.mynotes20.data

import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllNotesStream(): Flow<List<Note>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getNoteStream(id: Int): Flow<Note?>

    /**
     * Insert item in the data source
     */
    suspend fun insertNote(item: Note)

    /**
     * Delete item from the data source
     */
    suspend fun deleteNote(item: Note)

    /**
     * Update item in the data source
     */
    suspend fun updateNote(item: Note)
}