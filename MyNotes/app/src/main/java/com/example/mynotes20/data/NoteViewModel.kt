import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes20.data.Media
import com.example.mynotes20.data.MediaDao
import com.example.mynotes20.data.MediaTask
import com.example.mynotes20.data.MediaTaskDao
import com.example.mynotes20.data.Note
import com.example.mynotes20.data.NoteDao
import com.example.mynotes20.data.Task
import com.example.mynotes20.data.TaskDao
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

    fun upsertNoteAndMedia(note: Note, media: Media?, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                // Verificar si es una actualización o inserción
                if (note.id == 0) {
                    // Inserción
                    val noteId = noteDao.insert(note)
                    media?.let {
                        it.noteId = noteId.toInt() // Asignar el ID generado
                        noteDao.insertMedia(it) // Inserta el medio
                    }
                    onComplete(true) // Operación completada con éxito
                } else {
                    // Actualización
                    noteDao.update(note)
                    media?.let {
                        it.noteId = note.id // Usar el ID existente
                        noteDao.insertMedia(it) // Inserta o actualiza el medio
                    }
                    onComplete(true) // Operación completada con éxito
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onComplete(false) // Manejo de errores
            }
        }
    }

    fun upsertTaskAndMedia(task: Task, mediaTask: MediaTask?, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                // Verificar si es una actualización o inserción
                if (task.id == 0) {
                    // Inserción
                    val taskId = taskDao.insert(task)
                    mediaTask?.let {
                        it.taskId = taskId.toInt() // Asignar el ID generado
                        taskDao.insertMediaTask(it) // Inserta el medio
                    }
                    onComplete(true) // Operación completada con éxito
                } else {
                    // Actualización
                    taskDao.update(task)
                    mediaTask?.let {
                        it.taskId = task.id // Usar el ID existente
                        taskDao.insertMediaTask(it) // Inserta o actualiza el medio
                    }
                    onComplete(true) // Operación completada con éxito
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onComplete(false) // Manejo de errores
            }
        }
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
}
