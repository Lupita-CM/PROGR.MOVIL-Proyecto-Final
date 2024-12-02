package com.example.mynotes20.data

import kotlinx.coroutines.flow.Flow

class OfflineNotesRepository (private val NoteDao: NoteDao) : NotesRepository{
    override fun getAllNotesStream(): Flow<List<Note>> = NoteDao.getAllNotes()

    override fun getNoteStream(id: Int): Flow<Note?> = NoteDao.getNote(id)

    override suspend fun insertNote(item: Note): Long = NoteDao.insert(item)

    override suspend fun deleteNote(note: Note) = NoteDao.delete(note)

    override suspend fun updateNote(note: Note) = NoteDao.update(note)
}