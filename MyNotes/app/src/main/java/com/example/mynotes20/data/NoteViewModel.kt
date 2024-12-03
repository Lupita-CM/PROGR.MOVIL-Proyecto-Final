import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes20.data.Media
import com.example.mynotes20.data.MediaDao
import com.example.mynotes20.data.MediaTask
import com.example.mynotes20.data.MediaTaskDao
import com.example.mynotes20.data.Note
import com.example.mynotes20.data.NoteDao
import com.example.mynotes20.data.Reminders
import com.example.mynotes20.data.Task
import com.example.mynotes20.data.TaskDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.asStateFlow

class NoteViewModel(
    private val noteDao: NoteDao,
    private val taskDao: TaskDao,
    private val mediaDao: MediaDao,
    private val mediaTaskDao: MediaTaskDao
) : ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(mutableStateListOf())
    private val _tasks = MutableStateFlow<List<Task>>(mutableStateListOf())
    private val _media = MutableStateFlow<List<Media>>(mutableStateListOf())
    private val _mediaTask = MutableStateFlow<List<MediaTask>>(mutableStateListOf())

    val notes: StateFlow<List<Note>> = _notes.asStateFlow()
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()
    val media: StateFlow<List<Media>> = _media.asStateFlow()
    val mediaTask: StateFlow<List<MediaTask>> = _mediaTask.asStateFlow()

    init {
        getNotes()
        getTasks()
    }

    private fun getNotes() {
        viewModelScope.launch {
            noteDao.getAllNotes().collect { notes ->
                _notes.value = notes // Actualiza el valor del StateFlow
                notes.forEach { note ->
                    getMediaForNote(note.id)
                }
            }
        }
    }

    private fun getTasks() {
        viewModelScope.launch {
            taskDao.getAllTasks().collect { tasks ->
                _tasks.value = tasks // Actualiza el valor del StateFlow
                tasks.forEach { task ->
                    getMediaForTask(task.id)
                }
            }
        }
    }

    private fun getMediaForNote(noteId: Int) {
        viewModelScope.launch {
            noteDao.getMediaForNote(noteId).collect { mediaList ->
                _media.value = (_media.value + mediaList).distinct() // Agrega y elimina duplicados
            }
        }
    }

    private fun getMediaForTask(taskId: Int) {
        viewModelScope.launch {
            taskDao.getMediaForTask(taskId).collect { mediaTaskList ->
                _mediaTask.value = (_mediaTask.value + mediaTaskList).distinct() // Agrega y elimina duplicados
            }
        }
    }

    fun getMediasForNote(noteId: Int): Flow<List<Media>> = mediaDao.getMediaForNoteIdFlow(noteId)
    fun getMediasForTask(taskId: Int): Flow<List<MediaTask>> = mediaTaskDao.getMediaForTaskIdFlow(taskId)

    fun insert(note: Note) {
        viewModelScope.launch {
            noteDao.insert(note)
            getNotes() // Actualiza la lista después de insertar
        }
    }

    fun delete(note: Note) {
        viewModelScope.launch {
            noteDao.delete(note)
            getNotes() // Actualiza la lista después de eliminar
        }
    }

    fun insert(task: Task) {
        viewModelScope.launch {
            taskDao.insert(task)
            getTasks() // Actualiza la lista después de insertar
        }
    }

    fun delete(task: Task) {
        viewModelScope.launch {
            taskDao.delete(task)
            getNotes() // Actualiza la lista después de eliminar
        }
    }

    fun update(note: Note) {
        viewModelScope.launch {
            noteDao.update(note)
        }
    }

    fun update(task: Task) {
        viewModelScope.launch {
            taskDao.update(task)
        }
    }

    /*fun insert(media: Media) {
        viewModelScope.launch {
            mediaDao.insert(media)
            *//*getNotes() // Actualiza la lista después de insertar
            getTasks()*//*
        }
    }

    fun insert(mediaTask: MediaTask) {
        viewModelScope.launch {
            mediaTaskDao.insert(mediaTask)
            *//*getNotes() // Actualiza la lista después de insertar
            getTasks()*//*
        }
    }*/

    fun insert(media: Media) {
        viewModelScope.launch {
            mediaDao.insert(media)
        }
    }

    fun insert(mediaTask: MediaTask) {
        viewModelScope.launch {
            mediaTaskDao.insert(mediaTask)
        }
    }

    // Insertar tarea y obtener ID
    suspend fun insertTaskAndGetId(task: Task): Int {
//        var id: Int = -1
//        viewModelScope.launch {
//            id = taskDao.insert(task).toInt()
//        }
//        return id
        return taskDao.insert(task).toInt()
    }

    // Insertar tarea y obtener ID
    suspend fun insertNoteAndGetId(note: Note): Int {
//        var id: Int = -1
//        viewModelScope.launch {
//            id = noteDao.insert(note).toInt()
//        }
//        return id
        return noteDao.insert(note).toInt()
    }

    fun insertMediaTask(mediaTask: MediaTask) {
        viewModelScope.launch {
            taskDao.insertMediaTask(mediaTask)
        }
    }

    fun insertMedia(media: Media) {
        viewModelScope.launch {
            noteDao.insertMedia(media)
        }
    }

    // Para eliminar una media de la base de datos asociada a una nota
    fun deleteMedia(media: Media) {
        viewModelScope.launch {
            mediaDao.delete(media)
        }
    }

    // Para eliminar una media de la base de datos asociada a una tarea
    fun deleteMediaTask(mediaTask: MediaTask) {
        viewModelScope.launch {
            mediaTaskDao.delete(mediaTask)
        }
    }
}
