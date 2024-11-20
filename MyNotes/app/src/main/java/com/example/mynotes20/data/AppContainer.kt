package com.example.mynotes20.data

import android.content.Context

interface AppContainer {
    val noteRepository: NotesRepository
    val taskRepository: TasksRepository
    val mediaRepository: MediaRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    private val database = InventoryDatabase.getDatabase(context)

    override val noteRepository: NotesRepository by lazy {
        OfflineNotesRepository(database.noteDao())
    }

    override val taskRepository: TasksRepository by lazy {
        OfflineTasksRepository(database.taskDao())
    }

    override val mediaRepository: MediaRepository by lazy {
        OfflineMediaRepository(database.mediaDao())
    }
}