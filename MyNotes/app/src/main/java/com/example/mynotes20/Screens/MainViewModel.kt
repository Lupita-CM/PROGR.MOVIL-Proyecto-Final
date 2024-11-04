package com.example.mynotes20.Screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes20.data.Note
import com.example.mynotes20.data.NotesRepository
import com.example.mynotes20.data.Task
import com.example.mynotes20.data.TasksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel (
    private val noteRepository: NotesRepository,
    private val taskRepository: TasksRepository
): ViewModel(){
    // Observa las notas y tareas como StateFlow
    val notes: StateFlow<List<Note>> = noteRepository.getAllNotesStream()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    val tasks: StateFlow<List<Task>> = taskRepository.getAllTasksStream()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    // Método para actualizar una nota
    fun updateNote(note: Note) {
        viewModelScope.launch {
            noteRepository.updateNote(note) // Asume que update está definido en el repositorio
        }
    }

    // Método para eliminar una nota
    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepository.deleteNote(note) // Asume que delete está definido en el repositorio
        }
    }

    // Método para actualizar una tarea
    fun updateTask(task: Task) {
        viewModelScope.launch {
            taskRepository.updateTask(task) // Asume que update está definido en el repositorio
        }
    }

    // Método para eliminar una tarea
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task) // Asume que delete está definido en el repositorio
        }
    }
}