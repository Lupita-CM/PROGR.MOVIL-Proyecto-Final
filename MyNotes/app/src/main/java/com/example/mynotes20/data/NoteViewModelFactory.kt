package com.example.mynotes20.data

import NoteViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NoteViewModelFactory(
    private val noteDao: NoteDao,
    private val taskDao: TaskDao,
    private val mediaDao: MediaDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(noteDao, taskDao, mediaDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}