package com.example.mynotes20

import com.example.mynotes20.data.Media
import com.example.mynotes20.data.MediaRepository
import com.example.mynotes20.data.Note
import com.example.mynotes20.data.NotesRepository
import com.example.mynotes20.data.Task
import com.example.mynotes20.data.TasksRepository
import kotlinx.coroutines.flow.Flow

class CombinedRepository(
    private val noteRepository: NotesRepository,
    private val taskRepository: TasksRepository,
    private val mediaRepository: MediaRepository
) {
    suspend fun getAllNotes(): Flow<List<Note>> {
        return noteRepository.getAllNotesStream()
    }

    suspend fun getAllTasks(): Flow<List<Task>> {
        return taskRepository.getAllTasksStream()
    }

    suspend fun getAllMedia(): Flow<List<Media>> {
        return mediaRepository.getAllMediaStream()
    }
}